package com.licenta.committees.students;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MoveStudentRequestData {

    @JsonProperty("committeeId")
    public int committeeId;

    @JsonProperty("studentId")
    public int studentId;
}
