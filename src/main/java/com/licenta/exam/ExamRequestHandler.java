package com.licenta.exam;

import com.licenta.exam.committees.CommitteeStudentsData;
import com.licenta.exam.committees.CommitteesHandler;
import com.licenta.exam.grading.ExamGradingHandler;
import com.licenta.exam.grading.GradeRequestData;
import com.licenta.exam.grading.GradeResponseData;
import com.licenta.session.Session;
import com.licenta.session.SessionHandler;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("exam")
public class ExamRequestHandler {
    @GET
    @Path("{sessionId}/committeeStudents")
    @Produces(MediaType.APPLICATION_JSON)
    public CommitteeStudentsData getCommitteeStudentsBySession(@PathParam("sessionId") int sessionId) {
        Session session = SessionHandler.getSessionById(sessionId);
        return CommitteesHandler.getCommitteeStudents(session);
    }

    @GET
    @Path("committeeStudents/{committeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public CommitteeStudentsData getCommitteeStudents(@PathParam("committeeId") int committeeId) {
        return CommitteesHandler.getCommitteeStudents(committeeId);
    }

    @POST
    @Path("{sessionId}/grade")
    @Produces(MediaType.APPLICATION_JSON)
    public GradeResponseData handleGrading(@PathParam("sessionId") int sessionId, GradeRequestData data) {
        Session session = SessionHandler.getSessionById(sessionId);
        return ExamGradingHandler.addGrade(session, data);
    }
}
