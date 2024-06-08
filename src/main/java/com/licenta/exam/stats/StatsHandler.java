package com.licenta.exam.stats;

import com.licenta.db.DbConnectionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatsHandler {
    private static final String SQL_GET_ALL_STUDENTS_STATS = """
            SELECT s.idStud,
            coord.firstName as coordFirstName,
            coord.lastName as coordLastName,
            a.firstName as firstName,
            a.lastName as lastName,
            s.projectName as projectName,
            cs.committeeId as committeeId,
            eg.examGrade as examGrade,
            cg.grade as coordGrade,
            s.schoolGrade as schoolGrade
            FROM upt.students s
            JOIN upt.accounts a ON a.id = s.idStud
            JOIN upt.committeeStudents cs USING (idStud)
            JOIN upt.coordinatorGrades cg USING (idStud)
            JOIN (SELECT idStud, avg(mean) as examGrade FROM upt.examGrades GROUP BY idStud) eg USING (idStud)
            JOIN upt.accounts coord ON coord.id = s.coordinator;
            """;

    public static AllStudentsStatsResponseData getAllStudentsStats() {
        List<StudentStats> studentStats = new ArrayList<>();
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_STUDENTS_STATS);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                studentStats.add(getStudentStats(rs, connection));
            }
            connection.close();
        } catch (Exception e) {
            return new AllStudentsStatsResponseData();
        }
        for (StudentStats studentStat : studentStats) {
            studentStat.projectMean = getStudentProjectMean(studentStat.studentId);
            studentStat.knowledgeMean = getStudentKnowledgeMean(studentStat.studentId);
        }
        return new AllStudentsStatsResponseData(studentStats);
    }

    private static StudentStats getStudentStats(ResultSet rs, Connection connection) throws SQLException {
        StudentStats stats = new StudentStats();
        stats.studentId = rs.getInt("idStud");
        stats.studName = rs.getString("lastName") + " " + rs.getString("firstName");
        stats.projectName = rs.getString("projectName");
        stats.committeeId = rs.getInt("committeeId");
        stats.examGrade = rs.getFloat("examGrade");
        stats.schoolGrade = rs.getFloat("schoolGrade");
        stats.coordGrade = rs.getFloat("coordGrade");
        stats.coordName = rs.getString("coordLastName") + " " + rs.getString("coordFirstName");
        return stats;
    }

    private static final String SQL_GET_PROJECT_MEAN = """
            SELECT AVG(projectGrade) as projectMean FROM upt.examGrades WHERE idStud=?;
            """;
    private static float getStudentProjectMean(int studentId) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_PROJECT_MEAN);
            statement.setInt(1, studentId);
            ResultSet rs = statement.executeQuery();
            float projectMean = 0;
            while (rs.next()) {
                projectMean = rs.getFloat("projectMean");
            }
            connection.close();
            return projectMean;
        } catch (Exception e) {
            return 0;
        }
    }

    private static final String SQL_GET_KNOWLEDGE_MEAN = """
            SELECT AVG(knowledgeGrade) as knowledgeMean FROM upt.examGrades WHERE idStud=?;
            """;
    private static float getStudentKnowledgeMean(int studentId) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_KNOWLEDGE_MEAN);
            statement.setInt(1, studentId);
            ResultSet rs = statement.executeQuery();
            float knowledgeMean = 0;
            while (rs.next()) {
                knowledgeMean = rs.getFloat("knowledgeMean");
            }
            connection.close();
            return knowledgeMean;
        } catch (Exception e) {
            return 0;
        }
    }
}
