package com.licenta.announcements;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UploadConfigData {

    @JsonProperty("message")
    public String message;

    @JsonProperty("startDate")
    public int startDate;

    @JsonProperty("endDate")
    public int endDate;
}
