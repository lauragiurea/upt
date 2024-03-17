package com.licenta.coordinator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoordStudentData {

    @JsonProperty("firstName")
    public String firstName;

    @JsonProperty("lastname")
    public String lastname;

    @JsonProperty("projectName")
    public String projectName;

    @JsonProperty("schoolGrade")
    public float schoolGrade;
}
