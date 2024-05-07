package com.licenta.coordinator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoordGradeRequestData {

    @JsonProperty("email")
    public String email;

    @JsonProperty("grade")
    public float grade;
}
