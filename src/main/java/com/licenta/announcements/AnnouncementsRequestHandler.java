package com.licenta.announcements;

import com.licenta.common.JsonHelper;
import com.licenta.session.Session;
import com.licenta.session.SessionHandler;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("announcements")
public class AnnouncementsRequestHandler {

    @POST
    @Path("{sessionId}/add")
    @Produces(MediaType.TEXT_PLAIN)
    public String addAnnouncement(@PathParam("sessionId") int sessionId, AddAnnouncementRequestData data) throws Exception {
        if (data.message == null || data.message.isBlank())
            return JsonHelper.createEmptyJson();
        Session session = SessionHandler.getSessionById(sessionId);
        AnnouncementsHandler.addAnnouncement(session, data);
        return JsonHelper.createEmptyJson();
    }

    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    public AnnouncementsResponseData getAnnouncements() {
        return AnnouncementsHandler.getAnnouncements();
    }

    @POST
    @Path("projectUploadConfig")
    @Produces(MediaType.TEXT_PLAIN)
    public String configProjectUpload(UploadConfigData data) {
        UploadConfigHandler.configProjectUpload(data);
        return JsonHelper.createEmptyJson();
    }

    @POST
    @Path("presentationUploadConfig")
    @Produces(MediaType.TEXT_PLAIN)
    public String configPresentationUpload(UploadConfigData data) {
        UploadConfigHandler.configPresentationUpload(data);
        return JsonHelper.createEmptyJson();
    }

    @GET
    @Path("projectUploadConfig")
    @Produces(MediaType.APPLICATION_JSON)
    public UploadConfigData getProjectUploadConfig() {
        return UploadConfigHandler.getProjectUploadConfig();
    }

    @GET
    @Path("presentationUploadConfig")
    @Produces(MediaType.APPLICATION_JSON)
    public UploadConfigData getPresentationUploadConfig() {
        return UploadConfigHandler.getPresentationUploadConfig();
    }
}
