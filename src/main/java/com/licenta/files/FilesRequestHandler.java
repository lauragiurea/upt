package com.licenta.files;

import com.licenta.session.Session;
import com.licenta.session.SessionHandler;

import javax.ws.rs.*;
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
    public String handleUpload(@PathParam("sessionId") int sessionId, @PathParam("fileName") String fileName, byte[] fileBytes) throws Exception {
        Session session = SessionHandler.getSessionById(sessionId);
        new File("files/" + session.getUserId()).mkdirs();
        try (FileOutputStream stream = new FileOutputStream("files/" + session.getUserId() + "/" + fileName)) {
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

        Response.ResponseBuilder response = Response.ok((Object) project);
        response.header("Content-Disposition", "attachment; filename=file.pdf");
        response.type("application/pdf");
        return response.build();
    }
}
