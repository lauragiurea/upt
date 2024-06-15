package com.licenta.committees.students;

import com.licenta.db.DbConnectionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommitteesGenerator {

    private static final String SQL_GET_COMMITTEES = """
            SELECT committeeId, idStud, studLastName, studFirstName, lastName as coordLastName, firstName as coordFirstName FROM
                (SELECT committeeId, idStud, lastname as studLastName, firstName as studFirstName, coordinator FROM upt.committeeStudents
                JOIN upt.students USING (idStud)
                JOIN upt.accounts ON id = idStud) students
            JOIN upt.accounts on students.coordinator = id;
            """;
    public static CommitteeStudentsResponseData getCommittees() {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_COMMITTEES);
            ResultSet rs = statement.executeQuery();
            List<CommitteeStudentData> students = new ArrayList<>();
            while (rs.next()) {
                students.add(getStudentDetails(rs, true));
            }
            connection.close();
            return new CommitteeStudentsResponseData(students);
        } catch (Exception e) {
            return new CommitteeStudentsResponseData();
        }
    }

    public static void generateCommittees() {
        int committeesCount = getCommitteesCount();
        if (committeesCount == 0) {
            return;
        }
        List<CommitteeStudentData> students = getAllStudents();
        Collections.shuffle(students);
        students.forEach(student -> student.committeeId = (students.indexOf(student) % committeesCount) + 1);
        insertCommittees(students);
    }

    private static final String SQL_GET_COMMITTEES_COUNT = """
            SELECT max(committeeId) as maxId FROM upt.committeePresidents
            """;
    private static int getCommitteesCount() {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_COMMITTEES_COUNT);
            ResultSet rs = statement.executeQuery();
            int maxId = 0;
            while (rs.next()) {
                maxId = rs.getInt("maxId");
            }
            connection.close();
            return maxId;
        } catch (Exception e) {
            return 0;
        }
    }

    private static final String SQL_GET_ALL_STUDENTS = """
            SELECT idStud, studLastName, studFirstName, lastName as coordLastName, firstName as coordFirstName FROM
                (SELECT idStud, lastname as studLastName, firstName as studFirstName, coordinator FROM upt.students
                JOIN upt.accounts ON id = idStud) student
            JOIN upt.accounts on student.coordinator = id;
            """;
    public static List<CommitteeStudentData> getAllStudents() {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_STUDENTS);
            ResultSet rs = statement.executeQuery();
            List<CommitteeStudentData> students = new ArrayList<>();
            while (rs.next()) {
                students.add(getStudentDetails(rs, false));
            }
            connection.close();
            return students;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private static CommitteeStudentData getStudentDetails(ResultSet rs, boolean hasCommittee) throws SQLException {
        CommitteeStudentData student = new CommitteeStudentData();
        student.userId = rs.getInt("idStud");
        student.lastName = rs.getString("studLastName");
        student.firstName = rs.getString("studFirstName");
        student.coordinatorName = rs.getString("coordLastName") + " " + rs.getString("coordFirstName");
        if (hasCommittee) {
            student.committeeId = rs.getInt("committeeId");
        }
        return student;
    }

    private static final String SQL_INSERT_COMMITTEES = """
            INSERT IGNORE INTO upt.committeeStudents (committeeId, idStud)
            VALUES (?,?);
            """;
    private static void insertCommittees(List<CommitteeStudentData> students) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_COMMITTEES);
            for (CommitteeStudentData student : students) {
                statement.setInt(1, student.committeeId);
                statement.setInt(2, student.userId);
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
            connection.close();
        } catch (Exception e) {
        }
    }

    private static final String SQL_ARE_COMMITTEES_GENERATED = """
            SELECT COUNT(*) as count FROM upt.committeeStudents;
            """;
    public static boolean areCommitteesGenerated() {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_ARE_COMMITTEES_GENERATED);
            ResultSet rs = statement.executeQuery();
            boolean areCommitteesGenerated = false;
            while (rs.next()) {
                areCommitteesGenerated = rs.getInt("count") != 0;
            }
            connection.close();
            return areCommitteesGenerated;
        } catch (Exception e) {
            return false;
        }
    }

    private static final String SQL_UPDATE_COMMITTEE = """
            UPDATE upt.committeeStudents
            SET committeeId = ? WHERE idStud = ?
            """;
    public static void changeCommittee(MoveStudentRequestData data) throws Exception {
        if (data.committeeId > getCommitteesCount()) {
            throw new Exception("Invalid committee");
        }
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_COMMITTEE);
            statement.setInt(1, data.committeeId);
            statement.setInt(2, data.studentId);
            statement.execute();
            connection.close();
        } catch (Exception e) {
        }
    }
}
