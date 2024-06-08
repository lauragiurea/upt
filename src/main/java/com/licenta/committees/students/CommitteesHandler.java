package com.licenta.committees.students;

import com.licenta.committees.members.CommitteeMembersData;
import com.licenta.committees.members.CommitteeMembersHandler;
import com.licenta.db.DbConnectionHandler;
import com.licenta.exam.grading.ExamGradeResponseData;
import com.licenta.exam.grading.ExamGradeStatus;
import com.licenta.files.FilesHandler;
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
            (SELECT idStud, lastname, firstName, projectname, hour, schoolGrade, coordinator
            FROM upt.committeeStudents
            JOIN upt.students USING (idStud)
            JOIN upt.accounts ON id = idStud
            WHERE committeeId = ?) students
            JOIN
            (SELECT id, firstName as coordFirstName, lastName as coordLastName
            FROM upt.accounts) coordinators ON students.coordinator = coordinators.id
            LEFT JOIN upt.coordinatorGrades USING (idStud)
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
        students.forEach(student -> student.pvUploaded = FilesHandler.isPvUploaded(student.userId) ? 1 : 0);
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
        student.coordName = rs.getString("coordLastName") + " " + rs.getString("coordFirstName");
        student.coordGrade = rs.getFloat("grade");
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

    public static CommitteeMembersData getStudentCommittee(Session session) {
        int committeeId = getStudentCommitteeId(session.getUserId());
        return CommitteeMembersHandler.getCommittee(committeeId);
    }

    public static CommitteeMembersData getProfCommittee(Session session) {
        int committeeId = getCommitteeId(session.getUserId());
        return CommitteeMembersHandler.getCommittee(committeeId);
    }
}