package com.example.toychi.whattodo.ui;

import android.arch.lifecycle.ViewModel;

import com.example.toychi.whattodo.SubtaskDataSource;
import com.example.toychi.whattodo.TaskDataSource;
import com.example.toychi.whattodo.persistence.Subtask;
import com.example.toychi.whattodo.persistence.Task;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class SubtaskViewModel extends ViewModel {

    private final SubtaskDataSource mDataSource;

    private ArrayList<Subtask> subtasks;
    private ArrayList<String> names;

    public SubtaskViewModel(SubtaskDataSource dataSource) {
        mDataSource = dataSource;
    }

    public Flowable<ArrayList<Subtask>> getSubtasks(int tid) {
        return mDataSource.getSubtasks(tid)
                // for every emission of the user, get the user name
                .map(list -> {
                    subtasks = new ArrayList<Subtask>();
                    subtasks.addAll(list);
                    return subtasks;
                });
    }

    public Flowable<ArrayList<String>> getSubtasksName(int tid) {
        return mDataSource.getSubtasks(tid)
                // for every emission of the user, get the user name
                .map(list -> {
                    names = new ArrayList<String>();
                    for (Subtask subtask:list) {
                        names.add(subtask.getSubtaskName());
                    }
                    return names;
                });
    }

    /**
     * Update the task.
     *
     * @param
     * @return a {@link Completable} that completes when the user name is updated
     */
    public Completable addTask(int task_id, String subtaskName) {
        return Completable.fromAction(() -> {
            Subtask subtask = new Subtask(task_id, subtaskName);
            mDataSource.insertSubTask(subtask);
        });
    }

    public Completable deleteSubtask(int stid) {
        return Completable.fromAction(() -> {
            mDataSource.deleteSubtask(stid);
        });
    }

}
