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
                Session session = new Session(sessionId, userId, data.email);
                sessionsCache.putIfAbsent(sessionId, session);
                return new SessionNewResponseData(sessionId, session.getRole().toString());
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
    SELECT id FROM upt.accounts
    WHERE email = ? AND password = ?
    """;
    private static int getAccountId(String email, String password) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_CHECK_ACC);
            statement.setString(1, email);
            statement.setString(2, password);
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
}
