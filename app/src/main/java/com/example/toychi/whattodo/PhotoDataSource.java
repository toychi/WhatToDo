package com.example.toychi.whattodo;

import com.example.toychi.whattodo.persistence.Course;
import com.example.toychi.whattodo.persistence.Photo;

import java.util.List;

import io.reactivex.Flowable;

public interface PhotoDataSource {

    Flowable<List<Photo>> getPhoto(int tid);

    /**
     * Inserts the photo into the data source.
     *
     * @param photo the task to be inserted.
     */
    void insertPhoto(Photo photo);
}
