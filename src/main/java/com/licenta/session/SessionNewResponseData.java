package com.licenta.session;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionNewResponseData {

    public SessionNewResponseData(int sessionId, String role) {
        this.sessionId = sessionId;
        this.role = role;
    }

    @JsonProperty("sessionId")
    public int sessionId;

    @JsonProperty("role")
    public String role;
}
