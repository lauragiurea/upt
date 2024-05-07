package com.licenta.files;

import com.licenta.session.Session;
import com.licenta.session.SessionHandler;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Path("upload")
public class FileUploadHandler {

    @POST
    @Path("{sessionId}/{fileName}")
    public String handleUpload(@PathParam("sessionId") int sessionId, @PathParam("fileName") String fileName, byte[] fileBytes) throws Exception {
        Session session = SessionHandler.getSessionById(sessionId);
        new File("files/" + session.getUserId()).mkdirs();
        try (FileOutputStream stream = new FileOutputStream("files/" + session.getUserId() + "/" + fileName)) {
            stream.write(fileBytes);
            stream.close();
        }
        return "";
    }
}
