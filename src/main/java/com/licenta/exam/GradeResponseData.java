package com.licenta.exam;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GradeResponseData {
    @JsonProperty("mean")
    public float mean;

    @JsonProperty("status")
    public String status;
}
