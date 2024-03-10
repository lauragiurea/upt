package com.licenta.grading;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {
    @JsonProperty("lastName")
    public String lastName;

    @JsonProperty("firstName")
    public String firstName;

    @JsonProperty("projectName")
    public String projectName;

    @JsonProperty("hour")
    public String hour;

    @JsonProperty("schoolGrade")
    public Float schoolGrade;
}
