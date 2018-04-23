package com.example.toychi.whattodo;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.toychi.whattodo.persistence.Task;
import com.example.toychi.whattodo.ui.TaskViewModel;
import com.example.toychi.whattodo.ui.TaskViewModelFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mUserName;

    private EditText mTaskNameInput;

    private Button mUpdateButton;

    private TaskViewModelFactory mViewModelFactory;

    private TaskViewModel mViewModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModelFactory = Injection.provideViewModelFactory(this);
        mUserName = findViewById(R.id.textView);
        mUpdateButton = findViewById(R.id.button2);
        mTaskNameInput = findViewById(R.id.task_name_input);

        mViewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(TaskViewModel.class);
        mUpdateButton.setOnClickListener(v -> updateTaskName());

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Subscribe to the emissions of the user name from the view model.
        // Update the user name text view, at every onNext emission.
        // In case of error, log the exception.
        mDisposable.add(mViewModel.getOne()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userName -> mUserName.setText(userName),
                        throwable -> Log.e(TAG, "Unable to update username", throwable)));
    }

    @Override
    protected void onStop() {
        super.onStop();

        // clear all the subscriptions
        mDisposable.clear();
    }

    private void updateTaskName() {
        String taskName = mTaskNameInput.getText().toString();
        // Disable the update button until the user name update has been done
        mUpdateButton.setEnabled(false);
        // Subscribe to updating the user name.
        // Re-enable the button once the user name has been updated
        mDisposable.add(mViewModel.updateUserName(taskName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> mUpdateButton.setEnabled(true),
                        throwable -> Log.e(TAG, "Unable to update username", throwable)));
    }

}
