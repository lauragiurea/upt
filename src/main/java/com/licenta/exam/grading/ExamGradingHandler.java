package com.licenta.exam.grading;

import com.licenta.committees.CommitteeData;
import com.licenta.committees.CommitteeMembersHandler;
import com.licenta.db.DbConnectionHandler;
import com.licenta.exam.committees.CommitteesHandler;
import com.licenta.session.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;

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

    private static final String SQL_GET_OTHER_GRADES = """
            SELECT students.idStud, a.lastName as profLastName, a.firstName as profFirstName,
            studLastName, studFirstName, projectGrade, knowledgeGrade
            FROM
            (SELECT idStud, idProf, a.lastName as studLastName, a.firstName as studFirstName,
            eg.projectGrade as projectGrade, eg.knowledgeGrade as knowledgeGrade
            FROM upt.examGrades eg
            JOIN upt.accounts a ON a.id = eg.idStud) students
            JOIN upt.accounts a ON students.idProf = a.id
            JOIN upt.committeeStudents cs ON students.idStud = cs.idStud
            WHERE committeeId = ?;
            """;
    public static OtherGradesResponseData getOtherGrades(int userId) {
        OtherGradesResponseData response = new OtherGradesResponseData();
        int committeeId = CommitteesHandler.getCommitteeId(userId);
        CommitteeData committeeData = CommitteeMembersHandler.getCommittee(committeeId);
        List<String> professors = Stream.concat(committeeData.members.stream(), Stream.of(committeeData.president)).sorted().toList();
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_OTHER_GRADES);
            statement.setInt(1, committeeId);
            ResultSet rs = statement.executeQuery();
            List<StudentGradesData> students = new ArrayList<>();
            while (rs.next()) {
                StudentGradesData gradesData = new StudentGradesData();
                gradesData.idStud = rs.getInt("idStud");
                gradesData.studName = rs.getString("studLastName") + " " + rs.getString("studFirstName");
                gradesData.grades = new ArrayList<>();
                if (students.contains(gradesData)) {
                    for(StudentGradesData data : students) {
                        if (data.equals(gradesData)) {
                            data.grades.add(getGrade(rs));
                            data.grades.sort(Comparator.comparing(a -> a.profName));
                        }
                    }
                } else {
                    gradesData.grades.add(getGrade(rs));
                    students.add(gradesData);
                }
            }
            professors.forEach(prof -> {
                for(StudentGradesData data : students) {
                    if (!data.grades.stream().map(grade -> grade.profName).toList().contains(prof)) {
                        ExamGrade grade = new ExamGrade();
                        grade.profName = prof;
                        grade.projectGrade = 0;
                        grade.knowledgeGrade = 0;
                        data.grades.add(grade);
                    }
                }
            });
            for(StudentGradesData data : students) {
                data.grades.sort(Comparator.comparing(a -> a.profName));
            }
            connection.close();
            response = new OtherGradesResponseData(students, professors);
        } catch (Exception e) {
            return new OtherGradesResponseData();
        }
        response.grades.forEach(grade -> grade.status = checkGrade(grade.idStud, grade.getMean()).toString());
        return response;
    }

    private static ExamGrade getGrade(ResultSet rs) throws SQLException {
        ExamGrade grade = new ExamGrade();
        grade.profName = rs.getString("profLastName") + " " + rs.getString("profFirstName");
        grade.projectGrade = rs.getFloat("projectGrade");
        grade.knowledgeGrade = rs.getFloat("knowledgeGrade");
        return grade;
    }
}
