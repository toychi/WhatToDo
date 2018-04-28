package com.example.toychi.whattodo.persistence;

import com.example.toychi.whattodo.PhotoDataSource;

import java.util.List;

import io.reactivex.Flowable;

public class LocalPhotoDataSource implements PhotoDataSource {

    private final PhotoDao mPhotoDao;

    public LocalPhotoDataSource(PhotoDao photoDao) {
        mPhotoDao = photoDao;
    }

    @Override
    public Flowable<List<Photo>> getPhoto(int tid) {
        return mPhotoDao.getPhoto(tid);
    }

    @Override
    public void insertPhoto(Photo photo) {
        mPhotoDao.insertPhoto(photo);
    }
}
