package com.licenta.session;

public class Session {

    private final int sessionId;
    private final int userId;
    private final String email;

    private String lastName;
    private String firstName;

    private final AccountRole role;
    private Boolean isSecretary;
    private Boolean isCoordinator;
    private Boolean isCommitteeMember;

    public Session(int sessionId, int userId, String email) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.email = email;
        if (this.email == null || this.email.isBlank()) {
            this.role = AccountRole.NONE;
        } else if (this.email.contains("student")) {
            this.role = AccountRole.STUDENT;
        } else if (this.email.contains("secretariat")) {
            this.role = AccountRole.SECRETARY;
        } else {
            this.role = AccountRole.PROFESSOR;
        }
    }
    public int getSessionId() {
        return sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public AccountRole getRole() {
        return role;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setIsSecretary(boolean isSecretary) {
        this.isSecretary = isSecretary;
    }

    public Boolean isSecretary() {
        return this.isSecretary;
    }

    public void setIsCoordinator(boolean isCoordinator) {
        this.isCoordinator = isCoordinator;
    }

    public Boolean isCoordinator() {
        return this.isCoordinator;
    }

    public void setIsCommitteeMember(boolean isCommitteeMember) {
        this.isCommitteeMember = isCommitteeMember;
    }

    public Boolean isCommitteeMember() {
        return this.isCommitteeMember;
    }
}
