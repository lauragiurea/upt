package com.licenta.files;

import com.licenta.common.JsonHelper;
import com.licenta.session.Session;
import com.licenta.session.SessionHandler;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

@Path("file")
public class FilesRequestHandler {

    @POST
    @Path("{sessionId}/project/{fileName}")
    public String handleUploadProject(@PathParam("sessionId") int sessionId, @PathParam("fileName") String fileName,
                               byte[] fileBytes) throws Exception {
        Session session = SessionHandler.getSessionById(sessionId);
        FilesHandler.uploadProject(fileBytes, session, fileName);
        return JsonHelper.createEmptyJson();
    }

    @POST
    @Path("{sessionId}/presentation/{fileName}")
    public String handleUploadPresentation(@PathParam("sessionId") int sessionId, @PathParam("fileName") String fileName,
                               byte[] fileBytes) throws Exception {
        Session session = SessionHandler.getSessionById(sessionId);
        FilesHandler.uploadPresentation(fileBytes, session, fileName);
        return JsonHelper.createEmptyJson();
    }

    @POST
    @Path("{studentId}/appendix/{fileName}")
    public String handleUploadAppendix(@PathParam("fileName") String fileName, @PathParam("studentId") int studentId,
                               byte[] fileBytes) throws Exception {
        FilesHandler.uploadAppendix(fileBytes, studentId, fileName);
        return JsonHelper.createEmptyJson();
    }

    @POST
    @Path("{studentId}/pv/{fileName}")
    public String handleUploadPV(@PathParam("fileName") String fileName, @PathParam("studentId") int studentId,
                                        byte[] fileBytes) throws Exception {
        FilesHandler.uploadPV(fileBytes, studentId, fileName);
        return JsonHelper.createEmptyJson();
    }

    @GET
    @Path("{studentId}/project")
    @Produces("application/pdf")
    public Response handleDownloadProject(@PathParam("studentId") int studentId) {
        File project = FilesHandler.getProject(studentId);
        String fileName = project != null ? project.getName() : "file.pdf";

        Response.ResponseBuilder response = Response.ok((Object) project);
        response.header("Content-Disposition", "attachment; filename=" + fileName);
        response.type("application/pdf");
        return response.build();
    }

    @GET
    @Path("{studentId}/presentation")
    @Produces("application/pdf")
    public Response handleDownloadPresentation(@PathParam("studentId") int studentId) {
        File presentation = FilesHandler.getPresentation(studentId);
        String fileName = presentation != null ? presentation.getName() : "file.pdf";

        Response.ResponseBuilder response = Response.ok((Object) presentation);
        response.header("Content-Disposition", "attachment; filename=" + fileName);
        response.type("application/pdf");
        return response.build();
    }

    @GET
    @Path("{studentId}/appendix")
    @Produces("application/pdf")
    public Response handleDownloadAnexa4(@PathParam("studentId") int studentId) {
        File anexa4 = FilesHandler.getAppendix(studentId);
        String fileName = anexa4 != null ? anexa4.getName() : "file.pdf";

        Response.ResponseBuilder response = Response.ok((Object) anexa4);
        response.header("Content-Disposition", "attachment; filename=" + fileName);
        response.type("application/pdf");
        return response.build();
    }

    @GET
    @Path("{studentId}/pv")
    @Produces("application/pdf")
    public Response handleDownloadPV(@PathParam("studentId") int studentId) {
        File pv = FilesHandler.getPV(studentId);
        String fileName = pv != null ? pv.getName() : "file.pdf";

        Response.ResponseBuilder response = Response.ok((Object) pv);
        response.header("Content-Disposition", "attachment; filename=" + fileName);
        response.type("application/pdf");
        return response.build();
    }

    @GET
    @Path("{sessionId}/uploadedProject")
    @Produces(MediaType.APPLICATION_JSON)
    public StudentFileData handleGetStudentProject(@PathParam("sessionId") int sessionId) throws Exception {
        int userId = SessionHandler.getSessionById(sessionId).getUserId();

        StudentFileData fileData = new StudentFileData();
        File project = FilesHandler.getProject(userId);
        if (project != null) {
            fileData.fileName = project.getName();
        }
        return fileData;
    }

    @GET
    @Path("{sessionId}/uploadedPresentation")
    @Produces(MediaType.APPLICATION_JSON)
    public StudentFileData handleGetStudentPresentation(@PathParam("sessionId") int sessionId) throws Exception {
        int userId = SessionHandler.getSessionById(sessionId).getUserId();

        StudentFileData fileData = new StudentFileData();
        File presentation = FilesHandler.getPresentation(userId);
        if (presentation != null) {
            fileData.fileName = presentation.getName();
        }
        return fileData;
    }

    @GET
    @Path("{sessionId}/ownProject")
    @Produces("application/pdf")
    public Response handleDownloadOwnProject(@PathParam("sessionId") int sessionId) throws Exception {
        int studentId = SessionHandler.getSessionById(sessionId).getUserId();
        File project = FilesHandler.getProject(studentId);
        String fileName = project != null ? project.getName() : "file.pdf";

        Response.ResponseBuilder response = Response.ok((Object) project);
        response.header("Content-Disposition", "attachment; filename=" + fileName);
        response.type("application/pdf");
        return response.build();
    }

    @GET
    @Path("{sessionId}/ownPresentation")
    @Produces("application/pdf")
    public Response handleDownloadOwnPresentation(@PathParam("sessionId") int sessionId) throws Exception {
        int studentId = SessionHandler.getSessionById(sessionId).getUserId();
        File presentation = FilesHandler.getPresentation(studentId);
        String fileName = presentation != null ? presentation.getName() : "file.pdf";

        Response.ResponseBuilder response = Response.ok((Object) presentation);
        response.header("Content-Disposition", "attachment; filename=" + fileName);
        response.type("application/pdf");
        return response.build();
    }

    @GET
    @Path("appendixTemplate")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response handleDownloadAnexa4Template() {
        File anexa4 = new File("files/Anexa 4 ro - Fisa evaluare diploma-licenta.docx");

        Response.ResponseBuilder response = Response.ok((Object) anexa4);
        response.header("Content-Disposition", "attachment; filename=" + anexa4.getName());
        response.type(MediaType.APPLICATION_OCTET_STREAM);
        return response.build();
    }

    @GET
    @Path("pvTemplate")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response handleDownloadPVTemplate() {
        File pv = new File("files/PV_Sustinere.doc");

        Response.ResponseBuilder response = Response.ok((Object) pv);
        response.header("Content-Disposition", "attachment; filename=" + pv.getName());
        response.type(MediaType.APPLICATION_OCTET_STREAM);
        return response.build();
    }
}
