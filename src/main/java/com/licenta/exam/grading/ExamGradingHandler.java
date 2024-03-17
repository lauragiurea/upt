package com.licenta.exam.grading;

import com.licenta.db.DbConnectionHandler;
import com.licenta.session.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExamGradingHandler {

    private static final String SQL_ADD_GRADE = """
            INSERT INTO upt.examGrades (idProf, idStud, projectGrade, knowledgeGrade, mean)
            VALUES (?,?,?,?,?) as newValues
            ON DUPLICATE KEY UPDATE
            projectGrade = newValues.projectGrade,
            knowledgeGrade = newValues.knowledgeGrade,
            mean = newValues.mean;
            """;
    public static ExamGradeResponseData addGrade(Session session, ExamGradeRequestData data) {
        ExamGradeResponseData gradeResponseData = new ExamGradeResponseData();
        float mean = (data.projectGrade + data.knowledgeGrade) / 2;
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_GRADE);
            statement.setInt(1, session.getUserId());
            statement.setInt(2, data.studentId);
            statement.setFloat(3, data.projectGrade);
            statement.setFloat(4, data.knowledgeGrade);
            statement.setFloat(5, mean);
            statement.execute();
            connection.close();
        } catch (Exception e) {
            gradeResponseData.mean = 0;
            gradeResponseData.status = ExamGradeStatus.PENDING.toString();
            return gradeResponseData;
        }
        gradeResponseData.mean = mean;
        gradeResponseData.status = checkGrade(data.studentId, mean).toString();
        return gradeResponseData;
    }

    private static final String SQL_GET_SCHOOL_GRADE = """
            SELECT schoolGrade FROM upt.students
            WHERE idStud = ?;
            """;
    private static ExamGradeStatus checkGrade(int studentId, float grade) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_SCHOOL_GRADE);
            statement.setInt(1, studentId);
            ResultSet rs = statement.executeQuery();
            int schoolGrade = 0;
            while (rs.next()) {
                schoolGrade = rs.getInt("schoolGrade");
            }
            connection.close();
            return schoolGrade == 0 ? ExamGradeStatus.PENDING :
                    Math.abs(schoolGrade - grade) <= 2 ? ExamGradeStatus.OK : ExamGradeStatus.NOK;
        } catch (Exception e) {
            return ExamGradeStatus.PENDING;
        }
    }
}
