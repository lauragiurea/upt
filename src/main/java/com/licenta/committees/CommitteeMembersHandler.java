package com.licenta.committees;

import com.licenta.db.DbConnectionHandler;
import com.licenta.exam.grading.ExamGradeStatus;
import com.licenta.session.SessionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CommitteeMembersHandler {

    public static void addCommittee(CommitteeData data) throws Exception {
        addPresident(data.president, data.committeeId);
        addSecretary(data.secretary, data.committeeId);
        addMembers(data.members, data.committeeId);
    }

    private final static String SQL_ADD_PRESIDENT = """
            INSERT INTO upt.committeePresidents (idProf, committeeId)
            VALUES (?,?);
            """;
    private static void addPresident(String email, int committeeId) throws Exception {
        int userId = SessionHandler.getUserByEmail(email);
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_PRESIDENT);
            statement.setInt(1, userId);
            statement.setInt(2, committeeId);
            statement.execute();
            connection.close();
        } catch (Exception e) {
        }
    }

    private final static String SQL_ADD_SECRETARY = """
            INSERT INTO upt.committeeSecretaries (idProf, committeeId)
            VALUES (?,?);
            """;
    public static void addSecretary(String email, int committeeId) throws Exception {
        int userId = SessionHandler.getUserByEmail(email);
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_SECRETARY);
            statement.setInt(1, userId);
            statement.setInt(2, committeeId);
            statement.execute();
            connection.close();
        } catch (Exception e) {
        }
    }

    private final static String SQL_ADD_MEMBERS = """
            INSERT INTO upt.committeeMembers (idProf, committeeId)
            VALUES (?,?);
            """;
    public static void addMembers(List<String> emails, int committeeId) {
        List<Integer> userIds = new ArrayList<>();
        for (String email : emails) {
            try {
                int userId = SessionHandler.getUserByEmail(email);
                userIds.add(userId);
            } catch (Exception e) {
            }
        }
        try (Connection connection = DbConnectionHandler.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_MEMBERS);
            for (int userId : userIds) {
                statement.setInt(1, userId);
                statement.setInt(2, committeeId);
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
            connection.close();
        } catch (Exception e) {
        }
    }

    public static CommitteeData getCommittee(int committeeId) {
        CommitteeData data = new CommitteeData();
        data.committeeId = committeeId;
        data.president = getCommitteePresident(committeeId);
        data.secretary = getCommitteeSecretary(committeeId);
        data.members = getCommitteeMembers(committeeId);
        return data;
    }

    private final static String SQL_GET_COMMITTEE_PRESIDENT = """
            SELECT a.lastName as lastName, a.firstName as firstName FROM upt.committeePresidents p
            JOIN upt.accounts a ON p.idProf = a.id
            WHERE p.committeeId = ?;
            """;
    private static String getCommitteePresident(int committeeId) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_COMMITTEE_PRESIDENT);
            statement.setInt(1, committeeId);
            ResultSet rs = statement.executeQuery();
            String name = null;
            while (rs.next()) {
                name = rs.getString("lastName") + " " + rs.getString("firstName");
            }
            connection.close();
            return name;
        } catch (Exception e) {
            return null;
        }
    }

    private final static String SQL_GET_COMMITTEE_SECRETARY = """
            SELECT a.lastName as lastName, a.firstName as firstName FROM upt.committeeSecretaries s
            JOIN upt.accounts a ON s.idProf = a.id
            WHERE s.committeeId = ?;
            """;
    private static String getCommitteeSecretary(int committeeId) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_COMMITTEE_SECRETARY);
            statement.setInt(1, committeeId);
            ResultSet rs = statement.executeQuery();
            String name = null;
            while (rs.next()) {
                name = rs.getString("lastName") + " " + rs.getString("firstName");
            }
            connection.close();
            return name;
        } catch (Exception e) {
            return null;
        }
    }

    private final static String SQL_GET_COMMITTEE_MEMBERS = """
            SELECT a.lastName as lastName, a.firstName as firstName FROM upt.committeeMembers m
            JOIN upt.accounts a ON m.idProf = a.id
            WHERE m.committeeId = ?;
            """;
    private static List<String> getCommitteeMembers(int committeeId) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_COMMITTEE_MEMBERS);
            statement.setInt(1, committeeId);
            ResultSet rs = statement.executeQuery();
            List<String> members = new ArrayList<>();
            while (rs.next()) {
                members.add(rs.getString("lastName") + " " + rs.getString("firstName"));
            }
            connection.close();
            return members;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private final static String SQL_GET_COMMITTEES_COUNT = """
            SELECT max(committeeId) as count FROM upt.committeePresidents;
            """;
    public static String getCommitteesCount() {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_COMMITTEES_COUNT);
            ResultSet rs = statement.executeQuery();
            int count = 0;
            while (rs.next()) {
                count = rs.getInt("count");
            }
            connection.close();
            return Integer.valueOf(count).toString();
        } catch (Exception e) {
            return null;
        }
    }
}
