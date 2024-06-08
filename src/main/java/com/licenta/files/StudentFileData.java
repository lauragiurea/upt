package com.licenta.files;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class StudentFileData {

    @JsonProperty("isUploaded")
    public int isUploaded() {
        return fileName == null ? 0 : 1;
    }

    @JsonProperty("fileName")
    public String fileName;
}
