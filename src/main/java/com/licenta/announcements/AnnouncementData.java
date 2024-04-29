package com.licenta.announcements;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnnouncementData {

    @JsonProperty("author")
    public String author;

    @JsonProperty("message")
    public String message;

    @JsonProperty("time")
    public String time;
}
