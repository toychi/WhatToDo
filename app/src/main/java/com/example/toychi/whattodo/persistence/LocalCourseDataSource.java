package com.example.toychi.whattodo.persistence;

import android.arch.persistence.room.Dao;

import com.example.toychi.whattodo.CourseDataSource;

import java.util.List;

import io.reactivex.Flowable;

public class LocalCourseDataSource implements CourseDataSource {

    private final CourseDao mCourseDao;

    public LocalCourseDataSource(CourseDao courseDao) {
        mCourseDao = courseDao;
    }

    @Override
    public Flowable<List<Course>> getAllCourses() {
        return mCourseDao.getAllCourses();
    }

    @Override
    public void insertOrUpdateCourse(Course course) {
        mCourseDao.insertCourse(course);
    }

    @Override
    public void deleteCourse(int cid) {
        mCourseDao.deleteCourse(cid);
    }
}
