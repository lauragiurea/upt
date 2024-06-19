package com.licenta.coordinator;

import com.licenta.common.JsonHelper;
import com.licenta.session.Session;
import com.licenta.session.SessionHandler;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("coordinator/{sessionId}")
public class CoordinatorRequestHandler {

    @GET
    @Path("students")
    @Produces(MediaType.APPLICATION_JSON)
    public CoordStudentsData handleGetStudents(@PathParam("sessionId") int sessionId) throws Exception {
        Session session = SessionHandler.getSessionById(sessionId);
        return CoordStudentsHandler.getStudents(session);
    }

    @POST
    @Path("grade")
    @Produces(MediaType.TEXT_PLAIN)
    public String handleGrading(@PathParam("sessionId") int sessionId, CoordGradeRequestData data) throws Exception {
        Session session = SessionHandler.getSessionById(sessionId);
        CoordStudentsHandler.addGrade(session, data.studentId, data.grade);
        return JsonHelper.createEmptyJson();
    }
}
