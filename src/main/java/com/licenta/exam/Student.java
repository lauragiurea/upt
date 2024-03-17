package com.licenta.exam;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {
    @JsonProperty("userId")
    public int userId;

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
