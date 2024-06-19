package com.licenta.session;

import com.licenta.db.DbConnectionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SessionHandler {

    private static final Map<Integer, Session> sessionsCache = new HashMap<>();
    private static final Map<String, Integer> usersByEmail = new HashMap<>();

    public static int getUserByEmail(String email) throws Exception {
        int userId = usersByEmail.get(email) == null ? getUserFromDb(email) : usersByEmail.get(email);
        if (userId == 0) {
            throw new Exception("User not found!");
        }
        return userId;
    }

    private static final String SQL_GET_USER_FROM_DB = """
            SELECT id FROM upt.accounts
            WHERE email = ?;
            """;
    private static int getUserFromDb(String email) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_USER_FROM_DB);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            int userId = 0;
            while (rs.next()) {
                userId = rs.getInt("id");
            }
            connection.close();
            return userId;
        } catch (Exception e) {
            return 0;
        }
    }

    public static Session getSessionById(int sessionId) throws Exception {
        Session session = sessionsCache.get(sessionId) == null ? getSessionFromDB(sessionId) : sessionsCache.get(sessionId);
        if (session == null) {
            throw new Exception("Session not found!");
        }
        return session;
    }

    private static final String SQ_GET_SESSION_FROM_DB = """
    SELECT s.id as sessionId, accountId, email, lastName, firstName FROM upt.sessions s
    JOIN upt.accounts a ON s.accountId = a.id
    WHERE s.id = ?;
    """;
    private static Session getSessionFromDB(int sessionId) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQ_GET_SESSION_FROM_DB);
            statement.setInt(1, sessionId);
            ResultSet rs = statement.executeQuery();
            Session session = null;
            if (rs.next()) {
                session = getSession(rs);
                sessionsCache.putIfAbsent(sessionId, session);
            }
            connection.close();
            return session;
        } catch (Exception e) {
            return null;
        }
    }

    private static Session getSession(ResultSet rs) throws SQLException {
        Session session = new Session(rs.getInt("sessionId"),
                rs.getInt("accountId"),
                rs.getString("email"));
        session.setLastName(rs.getString("lastName"));
        session.setFirstName(rs.getString("firstName"));
        return session;
    }

    public static SessionNewResponseData generateNewSession(SessionNewRequestData data) throws Exception {
        int userId = getAccountId(data.email, data.password);
        if (userId != 0) {
            int sessionId = addNewSession(userId);
            if (sessionId != 0) {
                Session session = getSessionFromDB(sessionId);
                if (session == null) {
                    throw new Exception("Session could not be created");
                }
                if (session.getRole() == AccountRole.PROFESSOR) {
                    session.setIsSecretary(getIsSecretary(session.getUserId()));
                    session.setIsCoordinator(getIsCoordinator(session.getUserId()));
                    session.setIsCommitteeMember(getIsCommitteeMember(session.getUserId()));
                }
                sessionsCache.putIfAbsent(sessionId, session);
                usersByEmail.putIfAbsent(data.email, userId);
                return new SessionNewResponseData(sessionId, session.getRole().toString(), session.getFirstName(),
                        session.isSecretary(), session.isCoordinator(), session.isCommitteeMember());
            } else {
                throw new Exception("Session could not be created");
            }
        } else {
            throw new Exception("User not found");
        }
    }

    private static final String SQL_DELETE_SESSION = """
    DELETE FROM upt.sessions
    WHERE id = ?
    """;
    public static void deleteSession(int sessionId) throws Exception {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_SESSION);
            statement.setInt(1, sessionId);
            statement.execute();
            connection.close();
        } catch (Exception e) {
            throw new Exception("Session could not be deleted");
        }
        sessionsCache.remove(sessionId);
    }

    private static final String SQL_CHECK_ACC = """
    SELECT id, password FROM upt.accounts
    WHERE email = ?;
    """;
    private static int getAccountId(String email, String password) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_CHECK_ACC);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            int id = 0;
            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                if (PasswordValidator.validate(password, hashedPassword)) {
                    id = rs.getInt("id");
                }
            }
            connection.close();
            return id;
        } catch (Exception e) {
            return 0;
        }
    }

    private static final String SQL_ADD_SESSION = """
    INSERT INTO upt.sessions (accountId)
    VALUES (?);
    """;
    private static final String SQL_GET_SESSION_ID = """
    SELECT id FROM upt.sessions
    WHERE accountId = ?
    """;
    private static int addNewSession(int userId) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_SESSION);
            statement.setInt(1, userId);
            statement.execute();

            statement = connection.prepareStatement(SQL_GET_SESSION_ID);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            int id = 0;
            while (rs.next()) {
                id = rs.getInt("id");
            }
            connection.close();
            return id;
        } catch (Exception e) {
            return 0;
        }
    }

    private static final String SQL_GET_IS_SECRETARY = """
            SELECT COUNT(*) as count FROM upt.committeeSecretaries
            WHERE idProf = ?;
            """;
    private static boolean getIsSecretary(int userId) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_IS_SECRETARY);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            int count = 0;
            while (rs.next()) {
                count = rs.getInt("count");
            }
            connection.close();
            return count > 0;
        } catch (Exception e) {
            return false;
        }
    }

    private static final String SQL_GET_IS_COORDINATOR = """
            SELECT COUNT(*) as count FROM upt.students
            WHERE coordinator = ?;
            """;
    private static boolean getIsCoordinator(int userId) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_IS_COORDINATOR);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            int count = 0;
            while (rs.next()) {
                count = rs.getInt("count");
            }
            connection.close();
            return count > 0;
        } catch (Exception e) {
            return false;
        }
    }

    private static final String SQL_GET_IS_COMMITTEE_MEMBER = """
            SELECT COUNT(*) as count FROM
            (SELECT * FROM upt.committeePresidents
            UNION SELECT * FROM upt.committeeSecretaries
            UNION SELECT * FROM upt.committeeMembers) members
            WHERE members.idProf = ?;
            """;
    private static boolean getIsCommitteeMember(int userId) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_IS_COMMITTEE_MEMBER);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            int count = 0;
            while (rs.next()) {
                count = rs.getInt("count");
            }
            connection.close();
            return count > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
