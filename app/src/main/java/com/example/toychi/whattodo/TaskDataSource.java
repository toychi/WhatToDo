package com.example.toychi.whattodo;

import com.example.toychi.whattodo.persistence.Task;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * Access point for managing task data.
 */
public interface TaskDataSource {

    /**
     * Gets the user from the data source.
     *
     * @return the user from the data source.
     */
    Flowable<List<Task>> getTask();

    Flowable<Task> getOne();

    /**
     * Inserts the user into the data source, or, if this is an existing user, updates it.
     *
     * @param task the user to be inserted or updated.
     */
    void insertOrUpdateTask(Task task);

    /**
     * Deletes all users from the data source.
     */
    void deleteAllTasks();
}
