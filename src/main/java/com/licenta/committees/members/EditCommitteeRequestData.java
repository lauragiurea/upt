package com.licenta.committees.members;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.DefaultValue;

public class EditCommitteeRequestData {

    @JsonProperty("committeeId")
    public int committeeId;

    @JsonProperty("position")
    public int position;

    @JsonProperty("newValue")
    @DefaultValue("")
    public String newValue;
}
