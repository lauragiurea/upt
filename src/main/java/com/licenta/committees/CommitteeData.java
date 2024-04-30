package com.licenta.committees;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CommitteeData {

    @JsonProperty("committeeId")
    public int committeeId;

    @JsonProperty("president")
    public String president;

    @JsonProperty("secretary")
    public String secretary;

    @JsonProperty("members")
    public List<String> members;
}
