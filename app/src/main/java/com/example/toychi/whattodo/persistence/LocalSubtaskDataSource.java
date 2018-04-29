package com.example.toychi.whattodo.persistence;

import com.example.toychi.whattodo.SubtaskDataSource;
import com.example.toychi.whattodo.TaskDataSource;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Using the Room database as a data source.
 */
public class LocalSubtaskDataSource implements SubtaskDataSource {

    private final SubtaskDao mSubtaskDao;

    public LocalSubtaskDataSource(SubtaskDao subtaskDao) {
        mSubtaskDao = subtaskDao;
    }

    @Override
    public Flowable<List<Subtask>> getAllSubtasks() {
        return mSubtaskDao.getAllSubtasks();
    }

    @Override
    public Flowable<List<Subtask>> getSubtasks(int tid) {
        return mSubtaskDao.getSubtasks(tid);
    }

    @Override
    public void insertSubTask(Subtask subtask) {
        mSubtaskDao.insertSubtask(subtask);
    }

    @Override
    public void deleteSubtask(int stid) {
        mSubtaskDao.deleteSubtask(stid);
    }

    @Override
    public void deleteAllSubasks() {
        mSubtaskDao.deleteAllSubtasks();
    }
}
