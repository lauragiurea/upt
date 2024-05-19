package com.licenta.committees;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.licenta.exam.committees.CommitteesGenerator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("committees")
public class CommitteesRequestHandler {

    @GET
    @Path("getCommitteesCount")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCommitteesCount() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("count", CommitteeMembersHandler.getCommitteesCount());

        return mapper.writeValueAsString(rootNode);
    }

    @POST
    @Path("add")
    @Produces(MediaType.TEXT_PLAIN)
    public String addCommittee(CommitteeData data) throws Exception {
        CommitteeMembersHandler.addCommittee(data);
        return "";
    }

    @GET
    @Path("{committeeId}/get")
    @Produces(MediaType.APPLICATION_JSON)
    public CommitteeData getCommittee(@PathParam("committeeId") int committeeId) {
        return CommitteeMembersHandler.getCommittee(committeeId);
    }

    @POST
    @Path("editMember")
    @Produces(MediaType.TEXT_PLAIN)
    public String editCommitteeMember(EditCommitteeRequestData data) throws Exception {
        CommitteeMembersHandler.editCommittee(data);
        return "";
    }

    @POST
    @Path("deleteMember")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteCommitteeMember(EditCommitteeRequestData data) throws Exception {
        if (data.position >= 3) {
            CommitteeMembersHandler.deleteMember(data);
        }
        return "";
    }
}
