package com.example.toychi.whattodo.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(Course course);

    @Query("SELECT * FROM courses")
    Flowable<List<Course>> getAllCourses();

    @Query("DELETE FROM courses WHERE cid = :cid")
    void deleteCourse(int cid);
}
