package com.licenta.exam;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.licenta.exam.committees.CommitteeStudentsResponseData;
import com.licenta.exam.committees.CommitteesGenerator;
import com.licenta.exam.committees.ExamStudentsResponseData;
import com.licenta.exam.committees.CommitteesHandler;
import com.licenta.exam.grading.ExamGradingHandler;
import com.licenta.exam.grading.ExamGradeRequestData;
import com.licenta.exam.grading.ExamGradeResponseData;
import com.licenta.session.Session;
import com.licenta.session.SessionHandler;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("exam")
public class ExamRequestHandler {
    @GET
    @Path("{sessionId}/committeeStudents")
    @Produces(MediaType.APPLICATION_JSON)
    public ExamStudentsResponseData getCommitteeStudentsBySession(@PathParam("sessionId") int sessionId) throws Exception {
        Session session = SessionHandler.getSessionById(sessionId);
        return CommitteesHandler.getCommitteeStudents(session);
    }

    @GET
    @Path("committeeStudents/{committeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ExamStudentsResponseData getCommitteeStudents(@PathParam("committeeId") int committeeId) {
        return CommitteesHandler.getCommitteeStudents(committeeId);
    }

    @POST
    @Path("{sessionId}/grade")
    @Produces(MediaType.APPLICATION_JSON)
    public ExamGradeResponseData handleGrading(@PathParam("sessionId") int sessionId, ExamGradeRequestData data) throws Exception {
        Session session = SessionHandler.getSessionById(sessionId);
        return ExamGradingHandler.addGrade(session, data);
    }

    @GET
    @Path("areCommitteesGenerated")
    @Produces(MediaType.TEXT_PLAIN)
    public String areCommitteesGenerated() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("areCommitteesGenerated", CommitteesGenerator.areCommitteesGenerated());

        return mapper.writeValueAsString(rootNode);
    }

    @GET
    @Path("getOrGenerateCommittees")
    @Produces(MediaType.APPLICATION_JSON)
    public CommitteeStudentsResponseData generateCommittees() {
        return CommitteesGenerator.getOrGenerateCommittees();
    }

    @GET
    @Path("{idStud}/changeCommittee/{committeeId}/")
    @Produces(MediaType.TEXT_PLAIN)
    public String changeCommittee(@PathParam("idStud") int idStud, @PathParam("committeeId") int committeeId) throws Exception {
        CommitteesGenerator.changeCommittee(idStud, committeeId);
        return "";
    }
}
