package com.licenta.committees.students;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommitteeStudentData {
    @JsonProperty("userId")
    public int userId;

    @JsonProperty("lastName")
    public String lastName;

    @JsonProperty("firstName")
    public String firstName;

    @JsonProperty("coordinatorName")
    public String coordinatorName;

    @JsonProperty("committeeId")
    public int committeeId;
}
