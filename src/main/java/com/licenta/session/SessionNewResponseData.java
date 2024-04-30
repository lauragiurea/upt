package com.licenta.session;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionNewResponseData {

    public SessionNewResponseData(int sessionId, String role, String firstName) {
        this.sessionId = sessionId;
        this.role = role;
        this.firstName = firstName;
    }

    @JsonProperty("sessionId")
    public int sessionId;

    @JsonProperty("role")
    public String role;

    @JsonProperty("firstName")
    public String firstName;
}
