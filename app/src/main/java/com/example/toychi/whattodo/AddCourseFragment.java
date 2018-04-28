package com.example.toychi.whattodo;

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.toychi.whattodo.ui.CourseViewModel;
import com.example.toychi.whattodo.ui.CourseViewModelFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AddCourseFragment extends DialogFragment {

    private static final String TAG = AddCourseFragment.class.getSimpleName();

    private EditText courseName;

    // (Course) Room Database
    private CourseViewModelFactory mViewModelFactory;
    private CourseViewModel mViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // (Course) Room Database
        mViewModelFactory = Injection.provideCourseViewModelFactory(getActivity());
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(CourseViewModel.class);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_course, null);
        courseName = view.findViewById(R.id.coursename);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.add_course, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mDisposable.add(mViewModel.addCourse(courseName.getText().toString())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                        },
                                        throwable -> Log.e(TAG, "Unable to add task", throwable)));
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddCourseFragment.this.getDialog().cancel();
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // clear all the subscriptions
        mDisposable.clear();

    }
}
