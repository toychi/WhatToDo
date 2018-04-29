package com.example.toychi.whattodo.ui;

import android.arch.lifecycle.ViewModel;

import com.example.toychi.whattodo.TaskDataSource;
import com.example.toychi.whattodo.persistence.Task;

import java.util.ArrayList;

import javax.sql.DataSource;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TaskViewModel extends ViewModel {

    private final TaskDataSource mDataSource;

    private ArrayList<String> taskNames;
    private ArrayList<Task> tasks;

    public TaskViewModel(TaskDataSource dataSource) {
        mDataSource = dataSource;
    }

    /**
     * Get the names of the tasks.
     *
     * @return a {@link Flowable} that will emit every time the user name has been updated.
     */
    public Flowable<ArrayList<String>> getTaskNames() {
        return mDataSource.getAllTasks()
                // for every emission of the user, get the user name
                .map(list -> {
                    taskNames = new ArrayList<String>();
                    for (Task task:list) {
                        taskNames.add(task.getTaskName());
                    }
                    return taskNames;
                });
    }

    public Flowable<ArrayList<Task>> getTasks() {
        return mDataSource.getAllTasks()
                // for every emission of the user, get the user name
                .map(list -> {
                    tasks = new ArrayList<Task>();
                    tasks.addAll(list);
                    return tasks;
                });
    }

    public Flowable<Task> getTask(int tid) {
        return mDataSource.getTask(tid)
                // for every emission of the user, get the user name
                .map(task -> {
                    return task;
                });
    }

    public Flowable<ArrayList<Task>> getTasksByDate(String dueDate) {
        return mDataSource.getTasksByDate(dueDate)
                // for every emission of the user, get the user name
                .map(list -> {
                    tasks = new ArrayList<Task>();
                    tasks.addAll(list);
                    return tasks;
                });
    }

    /**
     * Update the task.
     *
     * @param
     * @return a {@link Completable} that completes when the user name is updated
     */
    public Completable updateTask(int tid, int course_id, String taskName, String taskDescription, String dueDate, String dueTime, int complete) {
        return Completable.fromAction(() -> {
            Task mTask = new Task(tid, course_id, taskName, taskDescription, dueDate, dueTime, complete);
            mDataSource.insertOrUpdateTask(mTask);
        });
    }

    /**
     * Update the task.
     *
     * @param
     * @return a {@link Completable} that completes when the user name is updated
     */
    public Completable addTask(int course_id, String taskName, String taskDescription, String dueDate, String dueTime) {
        return Completable.fromAction(() -> {
            Task mTask = new Task(course_id, taskName, taskDescription, dueDate, dueTime);
            mDataSource.insertOrUpdateTask(mTask);
        });
    }

    public Completable deleteTask(int tid) {
        return Completable.fromAction(() -> {
            mDataSource.deleteTask(tid);
        });
    }



}
