package com.example.toychi.whattodo.ui;

import android.arch.lifecycle.ViewModel;

import com.example.toychi.whattodo.TaskDataSource;
import com.example.toychi.whattodo.persistence.Task;

import java.util.ArrayList;

import javax.sql.DataSource;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TaskViewModel extends ViewModel {

    private final TaskDataSource mDataSource;

    private Task mUser;

    private ArrayList<String> userNames;

    public TaskViewModel(TaskDataSource dataSource) {
        mDataSource = dataSource;
    }

    /**
     * Get the user name of the user.
     *
     * @return a {@link Flowable} that will emit every time the user name has been updated.
     */
    public Flowable<ArrayList<String>> getUserName() {
        return mDataSource.getTask()
                // for every emission of the user, get the user name
                .map(list -> {
                    userNames = new ArrayList<String>();
                    for (Task name:list) {
                        userNames.add(name.getTaskName());
                    }
                    return userNames;
                });

    }

    public Flowable<String> getOne() {
        return mDataSource.getOne()
                .map(usr -> {
                    mUser = usr;
                    return mUser.getTaskName();
                });
    }

    /**
     * Update the user name.
     *
     * @param userName the new user name
     * @return a {@link Completable} that completes when the user name is updated
     */
    public Completable updateUserName(final String userName) {
        return Completable.fromAction(() -> {
            // if there's no use, create a new user.
            // if we already have a user, then, since the user object is immutable,
            // create a new user, with the id of the previous user and the updated user name.
            mUser = mUser == null
                    ? new Task(userName)
                    : new Task("k", userName);

            mDataSource.insertOrUpdateTask(mUser);
        });
    }

}
