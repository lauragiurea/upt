package com.licenta.exam.committees;

import com.licenta.committees.CommitteeData;
import com.licenta.committees.CommitteeMembersHandler;
import com.licenta.db.DbConnectionHandler;
import com.licenta.exam.grading.ExamGradeResponseData;
import com.licenta.exam.grading.ExamGradeStatus;
import com.licenta.exam.grading.ExamGradingHandler;
import com.licenta.exam.stats.AllStudentsStatsResponseData;
import com.licenta.exam.stats.StudentStats;
import com.licenta.session.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommitteesHandler {

    public static ExamStudentsResponseData getCommitteeStudents(Session session) {
        int committeeId = getCommitteeId(session.getUserId());
        return getCommitteeStudents(committeeId, session.getUserId());
    }

    private static final String SQL_GET_COMMITTEE_STUDENTS = """
            SELECT * FROM
            (SELECT idStud, lastname, firstName, projectname, hour, schoolGrade FROM upt.committeeStudents
            JOIN upt.students USING (idStud)
            JOIN upt.accounts ON id = idStud
            WHERE committeeId = ?) students
            LEFT JOIN
            (SELECT idStud, mean FROM upt.examGrades
            WHERE idProf = ?) grades USING (idStud)
            ;
            """;

    private static ExamStudentsResponseData getCommitteeStudents(int committeeId, int userId) {
        List<ExamStudentData> students = new ArrayList<>();
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_COMMITTEE_STUDENTS);
            statement.setInt(1, committeeId);
            statement.setInt(2, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                students.add(getStudentDetails(rs));
            }
            connection.close();
        } catch (Exception e) {
            return new ExamStudentsResponseData();
        }
        students.forEach(student -> student.examGrade.status = checkGrade(student.examGrade.mean, student.schoolGrade).toString());
        return new ExamStudentsResponseData(students, committeeId);
    }

    private static ExamGradeStatus checkGrade(float grade, float schoolGrade) {
        return schoolGrade == 0 || grade == 0 ? ExamGradeStatus.PENDING :
                Math.abs(schoolGrade - grade) <= 2 ? ExamGradeStatus.OK : ExamGradeStatus.NOK;
    }

    private static ExamStudentData getStudentDetails(ResultSet rs) throws SQLException {
        ExamStudentData student = new ExamStudentData();
        student.userId = rs.getInt("idStud");
        student.lastName = rs.getString("lastName");
        student.firstName = rs.getString("firstName");
        student.projectName = rs.getString("projectName");
        student.hour = rs.getString("hour");
        student.schoolGrade = rs.getFloat("schoolGrade");
        student.examGrade = new ExamGradeResponseData();
        student.examGrade.mean = rs.getFloat("mean");
        return student;
    }

    private static final String SQL_GET_COMMITTE_ID = """
            SELECT committeeId FROM upt.committeeMembers m
            JOIN upt.committeePresidents p USING (committeeId)
            JOIN upt.committeeSecretaries s USING (committeeId)
            where m.idProf = ? || p.idProf = ? || s.idProf = ?;
            """;

    public static int getCommitteeId(int userId) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_COMMITTE_ID);
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            statement.setInt(3, userId);
            ResultSet rs = statement.executeQuery();
            int id = 0;
            while (rs.next()) {
                id = rs.getInt("committeeId");
            }
            connection.close();
            return id;
        } catch (Exception e) {
            return 0;
        }
    }

    private static final String SQL_GET_STUDENT_COMMITTEE = """
            SELECT committeeId FROM upt.committeeStudents
            where idStud = ?;
            """;

    private static int getStudentCommitteeId(int idStud) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_STUDENT_COMMITTEE);
            statement.setInt(1, idStud);
            ResultSet rs = statement.executeQuery();
            int id = 0;
            while (rs.next()) {
                id = rs.getInt("committeeId");
            }
            connection.close();
            return id;
        } catch (Exception e) {
            return 0;
        }
    }

    public static CommitteeData getStudentCommittee(Session session) {
        int committeeId = getStudentCommitteeId(session.getUserId());
        return CommitteeMembersHandler.getCommittee(committeeId);
    }

    public static CommitteeData getProfCommittee(Session session) {
        int committeeId = getCommitteeId(session.getUserId());
        return CommitteeMembersHandler.getCommittee(committeeId);
    }

    private static final String SQL_GET_ALL_STUDENTS_STATS = """
            SELECT a.firstName as firstName,
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
            JOIN (SELECT idStud, avg(mean) as examGrade FROM upt.examGrades GROUP BY idStud) eg USING (idStud);
            """;

    public static AllStudentsStatsResponseData getAllStudentsStats() {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_STUDENTS_STATS);
            ResultSet rs = statement.executeQuery();
            List<StudentStats> studentStats = new ArrayList<>();
            while (rs.next()) {
                studentStats.add(getStudentStats(rs, connection));
            }
            connection.close();
            return new AllStudentsStatsResponseData(studentStats);
        } catch (Exception e) {
            return new AllStudentsStatsResponseData();
        }
    }

    private static StudentStats getStudentStats(ResultSet rs, Connection connection) throws SQLException {
        StudentStats stats = new StudentStats();
        stats.studName = rs.getString("lastName") + " " + rs.getString("firstName");
        stats.projectName = rs.getString("projectName");
        stats.committeeId = rs.getInt("committeeId");
        stats.examGrade = rs.getFloat("examGrade");
        stats.schoolGrade = rs.getFloat("schoolGrade");
        stats.coordGrade = rs.getFloat("coordGrade");
        return stats;
    }
}