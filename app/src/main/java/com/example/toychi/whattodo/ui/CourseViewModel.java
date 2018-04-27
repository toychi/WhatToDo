package com.example.toychi.whattodo.ui;

import android.arch.lifecycle.ViewModel;

import com.example.toychi.whattodo.CourseDataSource;
import com.example.toychi.whattodo.persistence.Course;
import com.example.toychi.whattodo.persistence.Task;

import java.util.ArrayList;

import io.reactivex.Flowable;

public class CourseViewModel extends ViewModel {

    private final CourseDataSource mDataSource;

    private ArrayList<String> courseNames;
    private ArrayList<Course> courses;

    public CourseViewModel(CourseDataSource dataSource){
        mDataSource = dataSource;
    }

    /**
     * Get the names of the tasks.
     *
     * @return a {@link Flowable} that will emit every time the user name has been updated.
     */
    public Flowable<ArrayList<String>> getCourseNames() {
        return mDataSource.getAllCourses()
                // for every emission of the user, get the user name
                .map(list -> {
                    courseNames = new ArrayList<String>();
                    for (Course course:list) {
                        String temp = course.getCourseName() + Integer.toString(course.getCid());
                        courseNames.add(temp);
                    }
                    return courseNames;
                });
    }

    public Flowable<ArrayList<Course>> getCourses() {
        return mDataSource.getAllCourses()
                .map(list -> {
                    courses = new ArrayList<Course>();
                    for (Course course:list) {
                        courses.add(course);
                    }
                    return courses;
                });
    }

}
