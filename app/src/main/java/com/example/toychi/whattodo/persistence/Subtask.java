package com.example.toychi.whattodo.persistence;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "subtasks",
        foreignKeys = @ForeignKey(entity = Task.class,
                                  parentColumns = "tid",
                                  childColumns = "task_id",
                                  onDelete = CASCADE))
public class Subtask {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "stid")
    private int stid;

    @ColumnInfo(name = "task_id")
    private int task_id;

    @NonNull
    @ColumnInfo(name = "subtaskName")
    private String subtaskName;

    // 1 = complete state, 0 = incomplete
    @ColumnInfo(name = "complete")
    private int complete;

    // Getters and setters are ignored for brevity,
    // but they're required for Room to work.

    public Subtask(int task_id, String subtaskName) {
        this.task_id = task_id;
        this.subtaskName = subtaskName;
        this.complete = 0;
    }

    @Ignore
    public Subtask(int stid, int task_id, String subtaskName, int complete) {
        this.stid = stid;
        this.task_id = task_id;
        this.subtaskName = subtaskName;
        this.complete = complete;
    }

    public int getStid() {
        return stid;
    }

    public void setStid(int stid) {
        this.stid = stid;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    @NonNull
    public String getSubtaskName() {
        return subtaskName;
    }

    public void setSubtaskName(@NonNull String subtaskName) {
        this.subtaskName = subtaskName;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }
}
