package com.licenta.exam.grading;

public enum GradeStatus {
    OK(1),
    NOK(2),
    PENDING(3);

    public final int statusCode;

    GradeStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public String toString() {
        return Integer.toString(this.statusCode);
    }

    public static GradeStatus valueOfCode(int statusCode) {
        for (GradeStatus s : values()) {
            if (s.statusCode == statusCode) {
                return s;
            }
        }
        return GradeStatus.PENDING;
    }
}
