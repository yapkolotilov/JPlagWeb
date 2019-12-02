package com.kolotilov.jplagweb.models;

public class Match {

    private int id;
    private String name;
    private String content;
    private int taskId;

    public Match() {
    }

    public Match(String name, String content, int taskId) {
        this.name = name;
        this.content = content;
        this.taskId = taskId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
