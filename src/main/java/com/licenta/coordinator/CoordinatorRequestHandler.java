package com.licenta.coordinator;

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
    @Path("student")
    @Produces(MediaType.TEXT_PLAIN)
    public String handleAddStudent(@PathParam("sessionId") int sessionId, AddStudentRequestData data) throws Exception {
        Session session = SessionHandler.getSessionById(sessionId);
        int studentId = SessionHandler.getUserByEmail(data.email);
        CoordStudentsHandler.addStudent(session, studentId, data.projectName, data.schoolGrade);
        return "";
    }

    @DELETE
    @Path("student")
    @Produces(MediaType.TEXT_PLAIN)
    public String handleDeleteStudent(@PathParam("sessionId") int sessionId, DeleteStudentRequestData data) throws Exception {
        Session session = SessionHandler.getSessionById(sessionId);
        int studentId = SessionHandler.getUserByEmail(data.email);
        CoordStudentsHandler.deleteStudent(session, studentId);
        return "";
    }

    @POST
    @Path("grade")
    @Produces(MediaType.TEXT_PLAIN)
    public String handleGrading(@PathParam("sessionId") int sessionId, CoordGradeRequestData data) throws Exception {
        Session session = SessionHandler.getSessionById(sessionId);
        int studentId = SessionHandler.getUserByEmail(data.email);
        CoordStudentsHandler.addGrade(session, studentId, data.grade);
        return "";
    }
}
