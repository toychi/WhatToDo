package com.example.toychi.whattodo.persistence;

import com.example.toychi.whattodo.TaskDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * Using the Room database as a data source.
 */
public class LocalTaskDataSource implements TaskDataSource {

    private final TaskDao mTaskDao;

    public LocalTaskDataSource(TaskDao taskDao) {
        mTaskDao = taskDao;
    }

    @Override
    public Flowable<List<Task>> getAllTasks() {
        return mTaskDao.getAllTasks();
    }

    @Override
    public Flowable<Task> getTask(int tid) {
        return mTaskDao.getTask(tid);
    }

    @Override
    public void insertOrUpdateTask(Task task) {
        mTaskDao.insertTask(task);
    }

    @Override
    public void deleteTask(int tid) {
        mTaskDao.deleteTask(tid);
    }

    @Override
    public void deleteAllTasks() {
        mTaskDao.deleteAllTasks();
    }
}
