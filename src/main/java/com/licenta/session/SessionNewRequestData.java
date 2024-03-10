package com.licenta.session;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionNewRequestData {
    @JsonProperty("email")
    public String email;

    @JsonProperty("password")
    public String password;

    @JsonProperty("lastName")
    public String lastName;

    @JsonProperty("firstName")
    public String firstName;
}
