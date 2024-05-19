package com.licenta.session;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionNewResponseData {

    public SessionNewResponseData(int sessionId, String role, String firstName,
                                  Boolean isSecretary, Boolean isCoordinator, Boolean isCommitteeMember) {
        this.sessionId = sessionId;
        this.role = role;
        this.firstName = firstName;
        if (isCoordinator != null) {
            this.isCoordinator = isCoordinator ? 1 : 0;
        }
        if (isSecretary != null) {
            this.isSecretary = isSecretary ? 1 : 0;
        }
        if (isCommitteeMember != null) {
            this.isCommitteeMember = isCommitteeMember ? 1 : 0;
        }
    }

    @JsonProperty("sessionId")
    public int sessionId;

    @JsonProperty("role")
    public String role;

    @JsonProperty("firstName")
    public String firstName;

    @JsonProperty("isSecretary")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer isSecretary;

    @JsonProperty("isCoordinator")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer isCoordinator;

    @JsonProperty("isCommitteeMember")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer isCommitteeMember;
}
