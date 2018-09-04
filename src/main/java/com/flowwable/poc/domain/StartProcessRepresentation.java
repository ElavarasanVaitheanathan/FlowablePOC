package com.flowwable.poc.domain;

public class StartProcessRepresentation {

    private String assignee;
    private String processName;

    public String getAssignee() {
        return assignee;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
}
