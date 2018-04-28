package com.example.toychi.whattodo.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.toychi.whattodo.PhotoDataSource;

public class PhotoViewModelFactory implements ViewModelProvider.Factory {

    private final PhotoDataSource mDataSource;

    public PhotoViewModelFactory(PhotoDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PhotoViewModel.class)) {
            return (T) new PhotoViewModel(mDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
