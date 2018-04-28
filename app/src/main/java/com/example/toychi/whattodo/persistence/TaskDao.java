package com.example.toychi.whattodo.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task task);

    @Query("SELECT * FROM tasks")
    Flowable<List<Task>> getAllTasks();

    @Query("SELECT * FROM tasks WHERE dueDate LIKE :dueDate")
    Flowable<List<Task>> getTasksByDate(String dueDate);

    @Query("SELECT * FROM tasks WHERE tid = :tid LIMIT 1")
    Flowable<Task> getTask(int tid);

    @Query("DELETE FROM tasks")
    void deleteAllTasks();

    @Query("DELETE FROM tasks WHERE tid = :tid")
    void deleteTask(int tid);

}
