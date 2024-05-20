package com.licenta.files;

import com.licenta.session.Session;
import com.licenta.session.SessionHandler;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Path("file")
public class FilesRequestHandler {

    @POST
    @Path("upload/{sessionId}/{fileName}")
    public String handleUpload(@PathParam("sessionId") int sessionId, @PathParam("fileName") String fileName,
                               byte[] fileBytes) throws Exception {
        Session session = SessionHandler.getSessionById(sessionId);
        String directoryPath = "files/" + session.getUserId();
        new File(directoryPath).mkdirs();
        try (FileOutputStream stream = new FileOutputStream(directoryPath + "/" + fileName)) {
            stream.write(fileBytes);
            stream.close();
        }
        return "";
    }

    @POST
    @Path("professor/upload/{studentId}/{fileName}")
    public String handleUpload(@PathParam("fileName") String fileName, @PathParam("studentId") int studentId,
                               byte[] fileBytes) throws Exception {
        String directoryPath = "files/" + studentId;
        new File(directoryPath).mkdirs();

        try (FileOutputStream stream = new FileOutputStream(directoryPath + "/" + fileName)) {
            stream.write(fileBytes);
            stream.close();
        }
        return "";
    }

    @GET
    @Path("download/{studentId}")
    @Produces("application/pdf")
    public Response handleDownload(@PathParam("studentId") int studentId) {
        File project = null;

        File[] allFiles = new File("files/" + studentId).listFiles();
        if (allFiles != null) {
            for (File file : allFiles) {
                if(file.getName().toLowerCase().startsWith("documentatie")) {
                    project = file;
                }
            }
        }

        String fileName = project != null ? project.getName() : "file.pdf";
        Response.ResponseBuilder response = Response.ok((Object) project);
        response.header("Content-Disposition", "attachment; filename=" + fileName);
        response.type("application/pdf");
        return response.build();
    }

    @GET
    @Path("download/anexa4")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response handleDownloadAnexa4Template() {
        File anexa4 = new File("files/Anexa 4 ro - Fisa evaluare diploma-licenta.docx");

        Response.ResponseBuilder response = Response.ok((Object) anexa4);
        response.header("Content-Disposition", "attachment; filename=" + anexa4.getName());
        response.type(MediaType.APPLICATION_OCTET_STREAM);
        return response.build();
    }

    @GET
    @Path("download/pv")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response handleDownloadPVTemplate() {
        File pv = new File("files/PV_Sustinere.doc");

        Response.ResponseBuilder response = Response.ok((Object) pv);
        response.header("Content-Disposition", "attachment; filename=" + pv.getName());
        response.type(MediaType.APPLICATION_OCTET_STREAM);
        return response.build();
    }

    @GET
    @Path("download/anexa4/{studentId} ")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response handleDownloadAnexa4(@PathParam("studentId") int studentId) {
        File anexa4 = null;

        File[] allFiles = new File("files/" + studentId).listFiles();
        if (allFiles != null) {
            for (File file : allFiles) {
                if(file.getName().toLowerCase().startsWith("anexa")) {
                    anexa4 = file;
                }
            }
        }
        String fileName = anexa4 != null ? anexa4.getName() : "file.pdf";

        Response.ResponseBuilder response = Response.ok((Object) anexa4);
        response.header("Content-Disposition", "attachment; filename=" + fileName);
        response.type(MediaType.APPLICATION_OCTET_STREAM);
        return response.build();
    }

    @GET
    @Path("download/pv/{studentId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response handleDownloadPV(@PathParam("studentId") int studentId) {
        File pv = null;

        File[] allFiles = new File("files/" + studentId).listFiles();
        if (allFiles != null) {
            for (File file : allFiles) {
                if(file.getName().toLowerCase().startsWith("pv")) {
                    pv = file;
                }
            }
        }
        String fileName = pv != null ? pv.getName() : "file.pdf";

        Response.ResponseBuilder response = Response.ok((Object) pv);
        response.header("Content-Disposition", "attachment; filename=" + fileName);
        response.type(MediaType.APPLICATION_OCTET_STREAM);
        return response.build();
    }
}
