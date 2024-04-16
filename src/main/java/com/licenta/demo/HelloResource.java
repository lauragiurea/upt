package com.licenta.demo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("demo")
public class HelloResource {
    @GET
    @Path("get/{text}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getText(@PathParam("text") String text) {
        return text;
    }

    @POST
    @Path("post")
    @Produces(MediaType.APPLICATION_JSON)
    public String postText(String data) {
        return data;
    }

    @GET
    @Path("file")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
//    @Produces(MediaType.TEXT_PLAIN)
    public Response getFile() throws URISyntaxException, IOException {
//        public String getFile() throws URISyntaxException, IOException {

        File file = new File("/Users/lauragiurea/Desktop/LICENTA/lcenta/src/main/res/Giurea_Laura_proiect.pdf");
        byte[] bytes = Files.readAllBytes(file.toPath());
        File newFile = new File("file.pdf");
        try (FileOutputStream stream = new FileOutputStream("file.pdf")) {
            stream.write(bytes);
        }

//        URL folderURL = this.getClass().getClassLoader().getResource("files");
//        File newFile = new File(folderURL.toURI().getPath() + "/file.pdf");
//
//        Files.copy(file.toPath(), newFile.toPath());
//
//        return newFile.getAbsolutePath();

        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" ) //optional
                .build();
    }

}