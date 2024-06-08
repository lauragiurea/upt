package com.licenta.committees.members;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.DefaultValue;
import java.util.List;

public class CommitteeMembersData {

    @JsonProperty("committeeId")
    @DefaultValue("0")
    public int committeeId;

    @JsonProperty("president")
    public String president;

    @JsonProperty("secretary")
    public String secretary;

    @JsonProperty("members")
    public List<String> members;
}
