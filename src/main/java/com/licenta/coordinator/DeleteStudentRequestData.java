package com.licenta.coordinator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteStudentRequestData {
    @JsonProperty("email")
    public String email;
}
