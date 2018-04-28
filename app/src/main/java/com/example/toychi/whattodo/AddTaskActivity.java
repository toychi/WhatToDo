package com.example.toychi.whattodo;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModel;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.toychi.whattodo.ui.CourseViewModel;
import com.example.toychi.whattodo.ui.CourseViewModelFactory;
import com.example.toychi.whattodo.ui.TaskViewModel;
import com.example.toychi.whattodo.ui.TaskViewModelFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddTaskActivity extends AppCompatActivity {

    private static final String TAG = AddTaskActivity.class.getSimpleName();

    private Calendar myCalendar = Calendar.getInstance();
    private EditText editDate;
    private EditText editTime;

    private Button addTask;
    private EditText taskinput;
    private EditText desin;
    private int cid;

    // (Course) Room Database
    private CourseViewModelFactory mViewModelFactory;
    private CourseViewModel mViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    // (Task) Room Database
    private TaskViewModelFactory tViewModelFactory;
    private TaskViewModel tViewModel;
    private final CompositeDisposable tDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);

        // (Course) Room Database
        mViewModelFactory = Injection.provideCourseViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(CourseViewModel.class);

        // (Task) Room Database
        tViewModelFactory = Injection.provideTaskViewModelFactory(this);
        tViewModel = ViewModelProviders.of(this, tViewModelFactory).get(TaskViewModel.class);

        Spinner courseSpinner = findViewById(R.id.dropdown);
        mDisposable.add(mViewModel.getCourseNames()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(coursename -> {
                    ArrayAdapter tt = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line);
                    tt.addAll(coursename);
                    courseSpinner.setAdapter(tt);
                }, throwable -> Log.e("Error in AddTask activity", "Unable to load course", throwable)));

        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mDisposable.add(mViewModel.getCourseId(i)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(courseid -> {
                            cid =courseid;
                        }, throwable -> Log.e("Error in AddTask activity", "Unable to load course_id", throwable)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        editDate = findViewById(R.id.dateInput);
        editTime = findViewById(R.id.timeInput);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }
        };

        TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hour);
                myCalendar.set(Calendar.MINUTE, minute);
                updateTimeLabel();

            }
        };

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(AddTaskActivity.this, date,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                DatePicker datePicker = dialog.getDatePicker();
                datePicker.setMinDate(System.currentTimeMillis() - 1000);
                dialog.show();
            }
        });
        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(AddTaskActivity.this, time,
                        myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),true)
                        .show();
            }
        });

        taskinput = findViewById(R.id.taskinput);
        desin = findViewById(R.id.desin);
        addTask = findViewById(R.id.addTask);
        addTask.setOnClickListener(view -> updateTaskName());

    }

    private void updateDateLabel() {
        String myFormat = "dd/MM/yy"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDate.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateTimeLabel() {
        String myFormat = "HH:mm"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editTime.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateTaskName() {
        String taskName = taskinput.getText().toString();
        String description = desin.getText().toString();
        String dueDate = editDate.getText().toString();
        String dueTime = editTime.getText().toString();
        // Disable the update button until the user name update has been done
        addTask.setEnabled(false);
        // Subscribe to updating the user name.
        // Re-enable the button once the user name has been updated
        tDisposable.add(tViewModel.addTask(cid, taskName, description, dueDate, dueTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    addTask.setEnabled(true);
                    finish();
                        },
                        throwable -> Log.e(TAG, "Unable to add task", throwable)));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_subtaskview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.option) {
            Toast.makeText(AddTaskActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            showAddCourseDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showAddCourseDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new AddCourseFragment();
        dialog.show(getSupportFragmentManager(), "AddCourseFragment");
    }

    @Override
    protected void onStop() {
        super.onStop();

        // clear all the subscriptions
        mDisposable.clear();
        tDisposable.clear();
    }
}
