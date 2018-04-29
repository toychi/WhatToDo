package com.example.toychi.whattodo.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.toychi.whattodo.SubtaskDataSource;

public class SubtaskViewModelFactory implements ViewModelProvider.Factory {

    private final SubtaskDataSource mDataSource;

    public SubtaskViewModelFactory(SubtaskDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SubtaskViewModel.class)) {
            return (T) new SubtaskViewModel(mDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
