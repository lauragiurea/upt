package com.licenta.coordinator;

import com.licenta.session.Session;
import com.licenta.session.SessionHandler;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("{sessionId}/coordinator")
public class CoordinatorRequestHandler {

    @GET
    @Path("addStudent/{studentId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String handleAddStudent(@PathParam("sessionId") int sessionId, @PathParam("studentId") int studentId) throws Exception {
        Session session = SessionHandler.getSessionById(sessionId);
        CoordStudentsHandler.addStudent(session, studentId);
        return "";
    }

    @GET
    @Path("deleteStudent/{studentId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String handleDeleteStudent(@PathParam("sessionId") int sessionId, @PathParam("studentId") int studentId) throws Exception {
        Session session = SessionHandler.getSessionById(sessionId);
        CoordStudentsHandler.deleteStudent(session, studentId);
        return "";
    }

    @POST
    @Path("grade")
    @Produces(MediaType.TEXT_PLAIN)
    public String handleGrading(@PathParam("sessionId") int sessionId, CoordGradeRequestData data) throws Exception {
        Session session = SessionHandler.getSessionById(sessionId);
        CoordStudentsHandler.addGrade(session, data);
        return "";
    }
}
