package com.example.toychi.whattodo.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.toychi.whattodo.CourseDataSource;

public class CourseViewModelFactory implements ViewModelProvider.Factory {

    private final CourseDataSource mDataSource;

    public CourseViewModelFactory(CourseDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CourseViewModel.class)) {
            return (T) new CourseViewModel(mDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
