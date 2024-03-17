package com.licenta.coordinator;

import com.licenta.db.DbConnectionHandler;
import com.licenta.exam.grading.ExamGradeRequestData;
import com.licenta.exam.grading.ExamGradeResponseData;
import com.licenta.exam.grading.ExamGradeStatus;
import com.licenta.session.Session;

import javax.servlet.http.HttpSessionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CoordStudentsHandler {

    private static final String SQL_ADD_STUDENT = """
            INSERT INTO upt.coordinators (idProf, idStud)
            VALUES (?,?);
            """;
    public static void addStudent(Session session, int studentId) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_STUDENT);
            statement.setInt(1, session.getUserId());
            statement.setInt(2, studentId);
            statement.execute();
            connection.close();
        } catch (Exception e) {

        }
    }

    private static final String SQL_DELETE_STUDENT = """
            DELETE FROM upt.coordinators
            WHERE idProf = ? AND idStud = ?;
            """;
    public static void deleteStudent(Session session, int studentId) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_STUDENT);
            statement.setInt(1, session.getUserId());
            statement.setInt(2, studentId);
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
}
