package com.example.toychi.whattodo;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toychi.whattodo.persistence.Task;
import com.example.toychi.whattodo.ui.TaskViewModel;
import com.example.toychi.whattodo.ui.TaskViewModelFactory;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CalendarActivity extends AppCompatActivity {

    public Calendar month, itemmonth;// calendar instances.

    public CalendarAdapter adapter;// adapter instance
    public Handler handler;// for grabbing some event values for showing the dot
    // marker.
    public ArrayList<String> items; // container to store calendar items which
    // needs showing the event marker

    ListView simpleList;

    DateFormatSymbols dfs = new DateFormatSymbols();
    final private String[] months = dfs.getMonths();

    TextView selected_date;

    // Room Database
    private TaskViewModelFactory mViewModelFactory;
    private TaskViewModel mViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        month = Calendar.getInstance();
        itemmonth = (Calendar) month.clone();

        items = new ArrayList<String>();
        adapter = new CalendarAdapter(this,(GregorianCalendar) month);

        GridView gridview = findViewById(R.id.gridview);
        gridview.setAdapter(adapter);

        handler = new Handler();
        handler.post(calendarUpdater);

        final TextView title = findViewById(R.id.title);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

        selected_date = findViewById(R.id.selected_date);

        Locale locale = Locale.getDefault();
        selected_date.setText(month.get(Calendar.DATE) + " " + month.getDisplayName(Calendar.MONTH, Calendar.LONG, locale) + " " + month.get(Calendar.YEAR));


        ImageView previous = findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        ImageView next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();

            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                ((CalendarAdapter) parent.getAdapter()).setSelected(v);
                String selectedGridDate = CalendarAdapter.dayString
                        .get(position);
                String[] separatedTime = selectedGridDate.split("-");
                String gridvalueString = separatedTime[2].replaceFirst("^0*",
                        "");// taking last part of date. ie; 2 from 2012-12-02.
                int gridvalue = Integer.parseInt(gridvalueString);

                StringBuilder title = new StringBuilder(months[Integer.parseInt(separatedTime[1]) - 1] + " " + separatedTime[0]);
                // navigate to next or previous month on clicking offdays.
                if ((gridvalue > 10) && (position < 8)) {
                    setPreviousMonth();
                    refreshCalendar();
                } else if ((gridvalue < 7) && (position > 28)) {
                    setNextMonth();
                    refreshCalendar();
                } else {
                    title.insert(0,gridvalueString + " ");
                }
                ((CalendarAdapter) parent.getAdapter()).setSelected(v);

                showToast(selectedGridDate);
                selected_date.setText(title.toString());

            }
        });

        // Room Database
        mViewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(TaskViewModel.class);

        simpleList = (ListView) findViewById(R.id.list);
        // TaskListAdapter taskListAdapter = new TaskListAdapter(getApplicationContext(),new String[]{"a"});
        // simpleList.setAdapter(taskListAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Subscribe to the emissions of the user name from the view model.
        // Update the user name text view, at every onNext emission.
        // In case of error, log the exception.
        mDisposable.add(mViewModel.getUserName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(username -> {
                    TaskListAdapter tt = new TaskListAdapter(getApplicationContext(), username);
                    simpleList.setAdapter(tt);
                    }, throwable -> Log.e("Error in Calendar activity", "Unable to load task", throwable)));
    }

    @Override
    protected void onStop() {
        super.onStop();

        // clear all the subscriptions
        mDisposable.clear();
    }


        protected void setNextMonth() {
            if (month.get(Calendar.MONTH) == month.getActualMaximum(Calendar.MONTH)) {
                month.set((month.get(Calendar.YEAR) + 1),
                        month.getActualMinimum(Calendar.MONTH), 1);
            } else {
                month.set(Calendar.MONTH, month.get(Calendar.MONTH) + 1);
            }

        }

        protected void setPreviousMonth() {
            if (month.get(Calendar.MONTH) == month.getActualMinimum(Calendar.MONTH)) {
                month.set((month.get(Calendar.YEAR) - 1),
                        month.getActualMaximum(Calendar.MONTH), 1);
            } else {
                month.set(Calendar.MONTH, month.get(Calendar.MONTH) - 1);
            }

        }

        protected void showToast(String string) {
            Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

        }

        public void refreshCalendar() {
            TextView title = (TextView) findViewById(R.id.title);

            adapter.refreshDays();
            adapter.notifyDataSetChanged();
            handler.post(calendarUpdater); // generate some calendar items

            title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

            selected_date.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
        }

        public Runnable calendarUpdater = new Runnable() {

            @Override
            public void run() {
                items.clear();

                // Print dates of the current week
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String itemvalue;
                for (int i = 0; i < 7; i++) {
                    itemvalue = df.format(itemmonth.getTime());
                    itemmonth.add(Calendar.DATE, 1);
                    items.add("2012-09-12");
                    items.add("2012-10-07");
                    items.add("2012-10-15");
                    items.add("2012-10-20");
                    items.add("2012-11-30");
                    items.add("2012-11-28");
                }

                adapter.setItems(items);
                adapter.notifyDataSetChanged();
            }
        };

}
