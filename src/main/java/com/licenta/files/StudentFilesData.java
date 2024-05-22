package com.licenta.files;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class StudentFilesData {

    @JsonProperty("count")
    public int count;

    @JsonProperty("files")
    public List<String> files;

    public StudentFilesData() {
        this.count = 0;
        this.files = new ArrayList<>();
    }

    public StudentFilesData(List<String> files) {
        this.count = files.size();
        this.files = files;
    }
}
