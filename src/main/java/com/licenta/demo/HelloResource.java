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
    @Produces("application/pdf")
    public Response getFile() {
        File file = new File("/Users/lauragiurea/Desktop/Giurea_Laura_proiect.pdf");

        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename=file.pdf");
        response.type("application/pdf");
        return response.build();
    }

}