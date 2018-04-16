package com.example.toychi.whattodo.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.toychi.whattodo.TaskDataSource;

import javax.sql.DataSource;

public class TaskViewModelFactory implements ViewModelProvider.Factory {

    private final TaskDataSource mDataSource;

    public TaskViewModelFactory(TaskDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(mDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
