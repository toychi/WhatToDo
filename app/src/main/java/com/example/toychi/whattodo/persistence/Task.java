package com.example.toychi.whattodo.persistence;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity(tableName = "tasks")
public class Task {

    @NonNull
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    @NonNull

    @PrimaryKey
    @ColumnInfo(name = "tid")
    private String tid;

    @ColumnInfo(name = "taskName")
    private String taskName;

    // Getters and setters are ignored for brevity,
    // but they're required for Room to work.

    @Ignore
    public Task(String taskName) {
        this.tid = UUID.randomUUID().toString();
        this.taskName = taskName;
    }

    public Task(String tid, String taskName) {
        this.tid = tid;
        this.taskName = taskName;
    }
}
