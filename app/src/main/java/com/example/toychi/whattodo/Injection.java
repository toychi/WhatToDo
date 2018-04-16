package com.example.toychi.whattodo;

import android.content.Context;

import com.example.toychi.whattodo.persistence.LocalTaskDataSource;
import com.example.toychi.whattodo.persistence.TasksDatabase;
import com.example.toychi.whattodo.ui.TaskViewModelFactory;

import javax.sql.DataSource;

public class Injection {

    public static TaskDataSource provideUserDataSource(Context context) {
        TasksDatabase database = TasksDatabase.getInstance(context);
        return new LocalTaskDataSource(database.taskDao());
    }

    public static TaskViewModelFactory provideViewModelFactory(Context context) {
        TaskDataSource dataSource = provideUserDataSource(context);
        return new TaskViewModelFactory(dataSource);
    }

}
