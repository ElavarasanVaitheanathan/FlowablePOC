package com.flowwable.poc.domain;

public class TaskRepresentation {

    private String id;
    private String name;
    private String assignee;

    public TaskRepresentation(String id, String name, String assignee) {
        this.id = id;
        this.name = name;
        this.assignee = assignee;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}