package com.example.toychi.whattodo.persistence;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "photos",
        foreignKeys = @ForeignKey(entity = Task.class,
                parentColumns = "tid",
                childColumns = "task_id",
                onDelete = CASCADE))
public class Photo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tid")
    private int tid;

    @ColumnInfo(name = "task_id")
    private int task_id;

    @NonNull
    @ColumnInfo(name = "photoUri")
    private String photoUri;

    public Photo(int task_id, String photoUri) {
        this.task_id = task_id;
        this.photoUri = photoUri;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    @NonNull
    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(@NonNull String photoUri) {
        this.photoUri = photoUri;
    }
}
