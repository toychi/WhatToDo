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
    private ArrayList<Integer> courseIds;

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
                        courseNames.add(course.getCourseName());
                    }
                    return courseNames;
                });
    }

    public Flowable<Integer> getCourseId(int position) {
        return mDataSource.getAllCourses()
                .map(list -> {
                    courseIds = new ArrayList<Integer>();
                    for (Course course:list) {
                        courseIds.add(Integer.valueOf(course.getCid()));
                    }
                    return courseIds.get(position);
                });
    }
}
