package com.licenta.session;

import com.fasterxml.jackson.databind.util.JSONPObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("session")
public class SessionRequestHandler {
    @POST
    @Path("new")
    @Produces(MediaType.APPLICATION_JSON)
    public SessionNewResponseData handleNewSession(SessionNewRequestData data) throws Exception {
        return SessionHandler.generateNewSession(data);
    }

    @DELETE
    @Path("{sessionId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String handleDeleteSession(@PathParam("sessionId") int sessionId) throws Exception {
        SessionHandler.deleteSession(sessionId);
        return "";
    }
}
