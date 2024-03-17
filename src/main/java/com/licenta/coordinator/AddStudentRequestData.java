package com.licenta.coordinator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddStudentRequestData {

    @JsonProperty("email")
    public String email;

    @JsonProperty("projectName")
    public String projectName;

    @JsonProperty("schoolGrade")
    public float schoolGrade;
}
