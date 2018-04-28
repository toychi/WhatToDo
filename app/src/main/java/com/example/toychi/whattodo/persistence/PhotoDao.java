package com.example.toychi.whattodo.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPhoto(Photo photo);

    @Query("SELECT * FROM photos WHERE task_id = :tid")
    Flowable<List<Photo>> getPhoto(int tid);

}
