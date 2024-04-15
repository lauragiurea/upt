package com.licenta.demo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
    public Response getFile() {
        File file = new File("/Users/lauragiurea/Desktop/LICENTA/lcenta/src/main/res/Giurea_Laura_proiect.pdf");

        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" ) //optional
                .build();
    }

}