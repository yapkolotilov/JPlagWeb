package com.kolotilov.jplagweb.models;

/**
 * Task given to students.
 */
public class Task {

    private int id;
    private String name;
    private String description;
    private String userUsername;

    public Task(String name, String description, String userUsername) {
        this.name = name;
        this.description = description;
        this.userUsername = userUsername;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }
}
