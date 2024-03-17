package com.licenta.exam.grading;

public enum ExamGradeStatus {
    OK(1),
    NOK(2),
    PENDING(3);

    public final int statusCode;

    ExamGradeStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public String toString() {
        return Integer.toString(this.statusCode);
    }

    public static ExamGradeStatus valueOfCode(int statusCode) {
        for (ExamGradeStatus s : values()) {
            if (s.statusCode == statusCode) {
                return s;
            }
        }
        return ExamGradeStatus.PENDING;
    }
}
