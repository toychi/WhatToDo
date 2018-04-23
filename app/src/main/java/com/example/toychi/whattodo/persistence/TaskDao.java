package com.example.toychi.whattodo.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks")
    Flowable<List<Task>> getAll();

    @Query("SELECT * FROM tasks LIMIT 1")
    Flowable<Task> getOne();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task task);

    @Query("DELETE FROM tasks")
    void deleteAllTasks();

}
