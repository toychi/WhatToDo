package com.example.toychi.whattodo;

import android.content.Context;

import com.example.toychi.whattodo.persistence.AppDatabase;
import com.example.toychi.whattodo.persistence.LocalCourseDataSource;
import com.example.toychi.whattodo.persistence.LocalPhotoDataSource;
import com.example.toychi.whattodo.persistence.LocalSubtaskDataSource;
import com.example.toychi.whattodo.persistence.LocalTaskDataSource;
import com.example.toychi.whattodo.ui.CourseViewModelFactory;
import com.example.toychi.whattodo.ui.PhotoViewModelFactory;
import com.example.toychi.whattodo.ui.SubtaskViewModelFactory;
import com.example.toychi.whattodo.ui.TaskViewModelFactory;

import javax.sql.DataSource;

public class Injection {

    /*
        Task ViewModel
     */
    public static TaskDataSource provideTaskDataSource(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        return new LocalTaskDataSource(database.taskDao());
    }

    public static TaskViewModelFactory provideTaskViewModelFactory(Context context) {
        TaskDataSource dataSource = provideTaskDataSource(context);
        return new TaskViewModelFactory(dataSource);
    }

    /*
        Course ViewModel
     */
    public static CourseDataSource provideCourseDataSource(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        return new LocalCourseDataSource(database.courseDao());
    }

    public static CourseViewModelFactory provideCourseViewModelFactory(Context context) {
        CourseDataSource dataSource = provideCourseDataSource(context);
        return new CourseViewModelFactory(dataSource);
    }

    /*
        Photo ViewModel
     */
    public static PhotoDataSource providePhotoDataSource(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        return new LocalPhotoDataSource(database.photoDao());
    }

    public static PhotoViewModelFactory providePhotoViewModelFactory(Context context) {
        PhotoDataSource dataSource = providePhotoDataSource(context);
        return new PhotoViewModelFactory(dataSource);
    }

    /*
        Subtask ViewModel
    */
    public static SubtaskDataSource provideSubtaskDataSource(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        return new LocalSubtaskDataSource(database.subtaskDao());
    }

    public static SubtaskViewModelFactory provideSubtaskViewModelFactory(Context context) {
        SubtaskDataSource dataSource = provideSubtaskDataSource(context);
        return new SubtaskViewModelFactory(dataSource);
    }


}
