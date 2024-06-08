package com.licenta.exam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.licenta.committees.members.CommitteeMembersData;
import com.licenta.committees.students.CommitteeStudentsResponseData;
import com.licenta.committees.students.CommitteesGenerator;
import com.licenta.committees.students.ExamStudentsResponseData;
import com.licenta.committees.students.CommitteesHandler;
import com.licenta.exam.grading.ExamGradingHandler;
import com.licenta.exam.grading.ExamGradeRequestData;
import com.licenta.exam.grading.ExamGradeResponseData;
import com.licenta.exam.grading.OtherGradesResponseData;
import com.licenta.exam.stats.AllStudentsStatsResponseData;
import com.licenta.exam.stats.StatsHandler;
import com.licenta.session.Session;
import com.licenta.session.SessionHandler;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("exam")
public class ExamRequestHandler {

    @POST
    @Path("{sessionId}/grade")
    @Produces(MediaType.APPLICATION_JSON)
    public ExamGradeResponseData handleGrading(@PathParam("sessionId") int sessionId, ExamGradeRequestData data) throws Exception {
        Session session = SessionHandler.getSessionById(sessionId);
        return ExamGradingHandler.addGrade(session, data);
    }

    @GET
    @Path("stats")
    @Produces(MediaType.APPLICATION_JSON)
    public AllStudentsStatsResponseData getAllStudentsStats() {
        return StatsHandler.getAllStudentsStats();
    }

    @GET
    @Path("{sessionId}/otherGrades")
    @Produces(MediaType.APPLICATION_JSON)
    public OtherGradesResponseData getOtherGrades(@PathParam("sessionId") int sessionId) throws Exception {
        int userId = SessionHandler.getSessionById(sessionId).getUserId();
        return ExamGradingHandler.getOtherGrades(userId);
    }
}
