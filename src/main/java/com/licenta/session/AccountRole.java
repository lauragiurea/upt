package com.licenta.session;

public enum AccountRole {
    STUDENT("student"),
    PROFESSOR("professor"),
    SECRETARY("secretary"),
    NONE("");

    public final String role;

    AccountRole(String role) {
        this.role = role;
    }

    public String toString() {
        return this.role;
    }

    public static AccountRole valueOfString(String role) {
        for (AccountRole r : values()) {
            if (r.role.equals(role)) {
                return r;
            }
        }
        return AccountRole.NONE;
    }
}
