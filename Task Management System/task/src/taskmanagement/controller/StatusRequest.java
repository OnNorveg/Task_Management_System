package taskmanagement.controller;

import taskmanagement.entity.Status;

public class StatusRequest {

    private Status status;

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
}
