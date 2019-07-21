package com.example.RecklessLabs.TaskManager;

import lombok.Getter;

@Getter
class Task {

    private String name;
    private String desc;
    private long totalTime;


    Task(String name, long totalTime, String desc){
        this.name = name;
        this.desc = desc;
        this.totalTime = totalTime;
    }
}
