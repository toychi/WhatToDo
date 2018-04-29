package com.example.toychi.whattodo;

import com.example.toychi.whattodo.persistence.Subtask;
import com.example.toychi.whattodo.persistence.Task;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Access point for managing task data.
 */
public interface SubtaskDataSource {

    /**
     * Gets all tasks from the data source.
     *
     * @return list of tasks from the data source.
     */
    Flowable<List<Subtask>> getAllSubtasks();

    /**
     * @return the task from the data source.
     */
    Flowable<List<Subtask>> getSubtasks(int tid);

    /**
     * Inserts the task into the data source, or, if this is an existing user, updates it.
     *
     * @param subtask the task to be inserted or updated.
     */
    void insertSubTask(Subtask subtask);

    void deleteSubtask(int stid);

    /**
     * Deletes all users from the data source.
     */
    void deleteAllSubasks();
}
