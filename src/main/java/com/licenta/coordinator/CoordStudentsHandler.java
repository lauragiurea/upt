package com.licenta.coordinator;

import com.licenta.db.DbConnectionHandler;
import com.licenta.session.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoordStudentsHandler {

    private static final String SQL_ADD_STUDENT = """
            INSERT INTO upt.students (idStud, projectName, schoolGrade, coordinator)
            VALUES (?,?,?,?) as newValues
            ON DUPLICATE KEY UPDATE
            projectName = newValues.projectName,
            schoolGrade = newValues.schoolGrade;
            """;
    public static void addStudent(Session session, int studentId, String projectName, float schoolGrade) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_STUDENT);
            statement.setInt(1, studentId);
            statement.setString(2, projectName);
            statement.setFloat(3, schoolGrade);
            statement.setInt(4, session.getUserId());
            statement.execute();
            connection.close();
        } catch (Exception e) {

        }
    }

    private static final String SQL_DELETE_STUDENT = """
            DELETE FROM upt.students
            WHERE idStud = ? AND coordinator = ?;
            """;
    public static void deleteStudent(Session session, int studentId) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_STUDENT);
            statement.setInt(1, studentId);
            statement.setInt(2, session.getUserId());
            statement.execute();
            connection.close();
        } catch (Exception e) {

        }
    }

    private static final String SQL_ADD_GRADE = """
            INSERT INTO upt.coordinatorGrades (idProf, idStud, grade)
            VALUES (?,?,?);
            """;
    public static void addGrade(Session session, CoordGradeRequestData data) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_GRADE);
            statement.setInt(1, session.getUserId());
            statement.setInt(2, data.studentId);
            statement.setFloat(3, data.grade);
            statement.execute();
            connection.close();
        } catch (Exception e) {

        }
    }

    private static final String SQL_GET_COORD_STUDENTS = """
            SELECT email, projectName, schoolGrade
            FROM upt.students
            JOIN upt.accounts ON idStud = id
            WHERE coordinator = ?;
            """;
    public static CoordStudentsData getStudents(Session session) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_COORD_STUDENTS);
            statement.setInt(1, session.getUserId());
            ResultSet rs = statement.executeQuery();
            List<CoordStudentData> students = new ArrayList<>();
            while (rs.next()) {
                students.add(getStudentDetails(rs));
            }
            connection.close();
            return new CoordStudentsData(students);
        } catch (Exception e) {
            return new CoordStudentsData();
        }
    }

    private static CoordStudentData getStudentDetails(ResultSet rs) throws SQLException {
        CoordStudentData student = new CoordStudentData();
        student.email = rs.getString("email");
        student.projectName = rs.getString("projectName");
        student.schoolGrade = rs.getFloat("schoolGrade");
        return student;
    }
}
