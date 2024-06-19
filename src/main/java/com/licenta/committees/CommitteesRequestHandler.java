package com.licenta.committees;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.licenta.committees.members.CommitteeMembersData;
import com.licenta.committees.members.CommitteeMembersHandler;
import com.licenta.committees.members.EditCommitteeRequestData;
import com.licenta.committees.students.*;
import com.licenta.common.JsonHelper;
import com.licenta.session.Session;
import com.licenta.session.SessionHandler;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("committees")
public class CommitteesRequestHandler {

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCommitteesCount() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("count", CommitteeMembersHandler.getCommitteesCount());

        return mapper.writeValueAsString(rootNode);
    }

    @POST
    @Path("{committeeId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String addCommittee(@PathParam("committeeId") int committeeId, CommitteeMembersData data) throws Exception {
        data.committeeId = committeeId;
        CommitteeMembersHandler.addCommittee(data);
        return JsonHelper.createEmptyJson();
    }

    @GET
    @Path("{committeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public CommitteeMembersData getCommittee(@PathParam("committeeId") int committeeId) {
        return CommitteeMembersHandler.getCommittee(committeeId);
    }

    @POST
    @Path("member")
    @Produces(MediaType.TEXT_PLAIN)
    public String editCommitteeMember(EditCommitteeRequestData data) throws Exception {
        CommitteeMembersHandler.editCommittee(data);
        return JsonHelper.createEmptyJson();
    }

    @DELETE
    @Path("member")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteCommitteeMember(EditCommitteeRequestData data) throws Exception {
        if (data.position >= 3) {
            CommitteeMembersHandler.deleteMember(data);
        }
        return JsonHelper.createEmptyJson();
    }

    @GET
    @Path("{sessionId}/students")
    @Produces(MediaType.APPLICATION_JSON)
    public ExamStudentsResponseData getCommitteeStudentsBySession(@PathParam("sessionId") int sessionId) throws Exception {
        Session session = SessionHandler.getSessionById(sessionId);
        return CommitteesHandler.getCommitteeStudents(session);
    }

    @GET
    @Path("areGenerated")
    @Produces(MediaType.TEXT_PLAIN)
    public String areCommitteesGenerated() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("areCommitteesGenerated", CommitteesGenerator.areCommitteesGenerated());

        return mapper.writeValueAsString(rootNode);
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public CommitteeStudentsResponseData getCommittees() {
        return CommitteesGenerator.getCommittees();
    }

    @POST
    @Path("generate")
    @Produces(MediaType.APPLICATION_JSON)
    public String generateCommittees() {
        CommitteesGenerator.generateCommittees();
        return JsonHelper.createEmptyJson();
    }

    @POST
    @Path("moveStudent")
    @Produces(MediaType.TEXT_PLAIN)
    public String changeCommittee(MoveStudentRequestData data) throws Exception {
        CommitteesGenerator.changeCommittee(data);
        return JsonHelper.createEmptyJson();
    }

    @GET
    @Path("student/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public CommitteeMembersData getStudentCommittee(@PathParam("sessionId") int sessionId) throws Exception {
        Session session = SessionHandler.getSessionById(sessionId);
        return CommitteesHandler.getStudentCommittee(session);
    }

    @GET
    @Path("prof/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public CommitteeMembersData getProfCommittee(@PathParam("sessionId") int sessionId) throws Exception {
        Session session = SessionHandler.getSessionById(sessionId);
        return CommitteesHandler.getProfCommittee(session);
    }
}
