package com.licenta.grading;

import com.licenta.db.DbConnectionHandler;
import com.licenta.session.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommitteesHandler {

    public static CommitteeStudentsData getCommitteeStudents(Session session) {
        int committeeId = getCommitteeId(session);
        return getCommitteeStudents(committeeId);
    }

    private static final String SQL_GET_COMMITTEE_STUDENTS = """
            SELECT lastname, firstName, projectname, hour, schoolGrade FROM upt.committeeStudents
            JOIN upt.students USING (idStud)
            JOIN upt.accounts ON id = idStud
            WHERE committeeId = ?;
            """;
    public static CommitteeStudentsData getCommitteeStudents(int committeeId) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_COMMITTEE_STUDENTS);
            statement.setInt(1, committeeId);
            ResultSet rs = statement.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                students.add(getStudentDetails(rs));
            }
            connection.close();
            return new CommitteeStudentsData(students);
        } catch (Exception e) {
            return new CommitteeStudentsData();
        }
    }

    private static Student getStudentDetails(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.lastName = rs.getString("lastName");
        student.firstName = rs.getString("firstName");
        student.projectName = rs.getString("projectName");
        student.hour = rs.getString("hour");
        student.schoolGrade = rs.getFloat("schoolGrade");
        return student;
    }

    private static final String SQL_GET_COMMITTE_ID = """
            SELECT committeeId FROM upt.committeeMembers m
            JOIN upt.committeePresidents p USING (committeeId)
            JOIN upt.committeeSecretaries s USING (committeeId)
            where m.idProf = ? || p.idProf = ? || s.idProf = ?;
            """;
    public static int getCommitteeId(Session session) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_COMMITTE_ID);
            statement.setInt(1, session.getUserId());
            statement.setInt(2, session.getUserId());
            statement.setInt(3, session.getUserId());
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

}
