package com.tabletka.model.requests;

public enum RequestStatus {
    APPROVED("APPROVED"),
    DENIED("DENIED"),
    PENDING("PENDING");

    private final String status;

    RequestStatus(final String status) {
        this.status = status;
    }

    public boolean isUndo() {
        return status.equals("APPROVED");
    }

    public boolean isApprove() {
        return !status.equals("APPROVED");
    }

    public boolean isReject() {
        return status.equals("PENDING");
    }
}
