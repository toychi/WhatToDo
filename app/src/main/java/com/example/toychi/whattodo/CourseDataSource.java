package com.example.toychi.whattodo;

import com.example.toychi.whattodo.persistence.Course;
import com.example.toychi.whattodo.persistence.Task;

import java.util.List;

import io.reactivex.Flowable;

public interface CourseDataSource {

    Flowable<List<Course>> getAllCourses();

    /**
     * Inserts the task into the data source, or, if this is an existing user, updates it.
     *
     * @param course the task to be inserted or updated.
     */
    void insertOrUpdateCourse(Course course);

    void deleteCourse(int cid);
}
