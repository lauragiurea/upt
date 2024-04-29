package com.licenta.announcements;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddAnnouncementRequestData {

    @JsonProperty("message")
    public String message;
}
