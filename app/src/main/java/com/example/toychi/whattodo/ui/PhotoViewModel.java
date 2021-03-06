package com.example.toychi.whattodo.ui;

import android.arch.lifecycle.ViewModel;
import android.net.Uri;

import com.example.toychi.whattodo.CourseDataSource;
import com.example.toychi.whattodo.PhotoDataSource;
import com.example.toychi.whattodo.persistence.Course;
import com.example.toychi.whattodo.persistence.Photo;
import com.example.toychi.whattodo.persistence.Task;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class PhotoViewModel extends ViewModel {

    private final PhotoDataSource mDataSource;

    private ArrayList<String> photoUris;

    public PhotoViewModel(PhotoDataSource dataSource){
        mDataSource = dataSource;
    }

    /**
     * Get the uri of the photos.
     *
     * @return a {@link Flowable} that will emit every time the user name has been updated.
     */
    public Flowable<ArrayList<String>> getPhotoUris(int tid) {
        return mDataSource.getPhoto(tid)
                // for every emission of the user, get the user name
                .map(list -> {
                    photoUris = new ArrayList<String>();
                    for (Photo photo:list) {
                        photoUris.add(photo.getPhotoUri());
                    }
                    return photoUris;
                });
    }

    /**
     * Add the photo.
     *
     * @param
     * @return a {@link Completable} that completes when the user name is updated
     */
    public Completable addPhoto(int tid, String uri) {
        return Completable.fromAction(() -> {
            Photo photo = new Photo(tid, uri);
            mDataSource.insertPhoto(photo);
        });
    }
}



