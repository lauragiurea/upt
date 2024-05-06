package com.licenta.exam.stats;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class AllStudentsStatsResponseData {

    @JsonProperty("count")
    int count;

    @JsonProperty("studentStats")
    List<StudentStats> studentStats;

    public AllStudentsStatsResponseData() {
        this.count = 0;
        this.studentStats = new ArrayList<>();
    }

    public AllStudentsStatsResponseData(List<StudentStats> studentStats) {
        this.count = studentStats.size();
        this.studentStats = studentStats;
    }
}
