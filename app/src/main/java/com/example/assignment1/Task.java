package com.example.assignment1;

public class Task {
    private String TaskName;
    private String TaskDescraption;

    public static final Task[]task = {
        new Task("Network","Network Project 8/12/2023")
    };

    public Task(String name, String Descraption) {
        this.TaskName=name;
        this.TaskDescraption=Descraption;

    }

    public String getTaskName() {
        return TaskName;
    }

    public String getTaskDescraption() {
        return TaskDescraption;
    }

    @Override
    public String toString() {
        return TaskName;
    }
}
