package com.licenta.committees.students;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.licenta.exam.grading.ExamGradeResponseData;

public class ExamStudentData {
    @JsonProperty("userId")
    public int userId;

    @JsonProperty("lastName")
    public String lastName;

    @JsonProperty("firstName")
    public String firstName;

    @JsonProperty("coordName")
    public String coordName;

    @JsonProperty("coordGrade")
    public float coordGrade;

    @JsonProperty("projectName")
    public String projectName;

    @JsonProperty("schoolGrade")
    public Float schoolGrade;

    @JsonProperty("examGrade")
    public ExamGradeResponseData examGrade;

    @JsonProperty("pvUploaded")
    public int pvUploaded;
}
