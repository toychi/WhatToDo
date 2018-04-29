package com.example.toychi.whattodo.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface SubtaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSubtask(Subtask subtask);

    @Query("SELECT * FROM subtasks")
    Flowable<List<Subtask>> getAllSubtasks();

    @Query("SELECT * FROM subtasks WHERE task_id = :tid")
    Flowable<List<Subtask>> getSubtasks(int tid);

    @Query("DELETE FROM subtasks")
    void deleteAllSubtasks();

    @Query("DELETE FROM subtasks WHERE stid = :stid")
    void deleteSubtask(int stid);

}
