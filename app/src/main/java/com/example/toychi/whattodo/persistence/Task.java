package com.example.toychi.whattodo.persistence;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "tasks",
        foreignKeys = @ForeignKey(entity = Course.class,
                                  parentColumns = "cid",
                                  childColumns = "course_id",
                                  onDelete = CASCADE))
public class Task {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tid")
    private int tid;

    @ColumnInfo(name = "course_id")
    private int course_id;

    @NonNull
    @ColumnInfo(name = "taskName")
    private String taskName;

    @ColumnInfo(name = "taskDescription")
    private String taskDescription;

    @ColumnInfo(name = "dueDate")
    private String dueDate;

    @ColumnInfo(name = "dueTime")
    private String dueTime;

    // 1 = complete state, 0 = incomplete
    @ColumnInfo(name = "complete")
    private int complete;

    // Getters and setters are ignored for brevity,
    // but they're required for Room to work.

    @Ignore
    public Task(int tid, int course_id, String taskName, String taskDescription, String dueDate, String dueTime) {
        this.tid = tid;
        this.course_id = course_id;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.complete = 0;
    }

    public Task(int course_id, String taskName, String taskDescription, String dueDate, String dueTime) {
        this.course_id = course_id;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.complete = 0;
    }

    @Ignore
    public Task(int tid, int course_id, String taskName, String taskDescription, String dueDate, String dueTime, int complete) {
        this.tid = tid;
        this.course_id = course_id;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.complete = complete;
    }

    @NonNull
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(@NonNull String taskName) {
        this.taskName = taskName;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }
}
