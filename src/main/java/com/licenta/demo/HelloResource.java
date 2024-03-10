package com.licenta.demo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
}