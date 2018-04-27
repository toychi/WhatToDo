package com.example.toychi.whattodo;

import com.example.toychi.whattodo.persistence.Course;

import java.util.List;

import io.reactivex.Flowable;

public interface CourseDataSource {

    Flowable<List<Course>> getAllCourses();

    void deleteCourse(int cid);
}
