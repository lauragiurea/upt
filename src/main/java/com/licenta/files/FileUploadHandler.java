package com.licenta.files;

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
    public Response handleAddStudent(byte[] fileBytes) throws Exception {
        File file = new File("file.pdf");
        try (FileOutputStream stream = new FileOutputStream("file.pdf")) {
            stream.write(fileBytes);
            stream.close();
        }
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
        .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" ) //optional
        .build();
    }

//    @POST
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response handleAddStudent(@FormDataParam("file") InputStream uploadedInputStream) throws Exception {
//        File file = new File("file.pdf");
//        Files.copy(uploadedInputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
////        try (FileOutputStream stream = new FileOutputStream("file.pdf")) {
////            stream.write(fileBytes);
////            Files.copy(uploadedInputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
////            stream.close();
////        }
//        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
//                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" ) //optional
//                .build();
//    }
}
