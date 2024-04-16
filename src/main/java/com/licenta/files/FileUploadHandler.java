package com.licenta.files;

import com.licenta.coordinator.AddStudentRequestData;
import com.licenta.coordinator.CoordStudentsHandler;
import com.licenta.session.Session;
import com.licenta.session.SessionHandler;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

@Path("upload")
public class FileUploadHandler {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response handleAddStudent(File file) throws Exception {
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
        .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" ) //optional
        .build();
    }
}
