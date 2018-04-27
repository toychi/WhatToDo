package com.example.toychi.whattodo;

import android.arch.persistence.room.Query;

import com.example.toychi.whattodo.persistence.Task;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * Access point for managing task data.
 */
public interface TaskDataSource {

    /**
     * Gets all tasks from the data source.
     *
     * @return list of tasks from the data source.
     */
    Flowable<List<Task>> getAllTasks();

    /**
     * @return the task from the data source.
     */
    Flowable<Task> getTask(int tid);

    /**
     * Inserts the task into the data source, or, if this is an existing user, updates it.
     *
     * @param task the task to be inserted or updated.
     */
    void insertOrUpdateTask(Task task);

    void deleteTask(int tid);

    /**
     * Deletes all users from the data source.
     */
    void deleteAllTasks();
}
