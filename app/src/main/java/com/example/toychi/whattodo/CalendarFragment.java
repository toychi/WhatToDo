package com.example.toychi.whattodo;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
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
import io.reactivex.schedulers.Schedulers;

public class CalendarFragment extends Fragment {

    public Calendar month, itemmonth; // Calendar instances
    public CalendarAdapter adapter; // Adapter instance
    public ListView simpleList; // Task list

    public Handler handler;// for grabbing some event values for showing the dot marker.
    public ArrayList<String> items; // container to store calendar items which needs showing the event marker.

    DateFormatSymbols dfs = new DateFormatSymbols();
    final private String[] months = dfs.getMonths(); // Months strings

    public TextView selected_date;
    public TextView title;

    // Room Database
    private TaskViewModelFactory mViewModelFactory;
    private TaskViewModel mViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        month = Calendar.getInstance();
        itemmonth = (Calendar) month.clone();
        items = new ArrayList<String>();
        adapter = new CalendarAdapter(getActivity(),(GregorianCalendar) month);

        // (Task) Room Database
        mViewModelFactory = Injection.provideTaskViewModelFactory(getActivity());
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(TaskViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        simpleList = (ListView) view.findViewById(R.id.list);

        GridView gridview = view.findViewById(R.id.gridview);
        gridview.setAdapter(adapter);
        updateEvents();
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                String selectedGridDate = CalendarAdapter.dayString
                        .get(position);
                String[] separatedTime = selectedGridDate.split("-");
                ((CalendarAdapter) parent.getAdapter()).setSelected(v, separatedTime);
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
                ((CalendarAdapter) parent.getAdapter()).setSelected(v, separatedTime);

                showToast(selectedGridDate);
                selected_date.setText(title.toString());

                String dueDate = separatedTime[2] + "/" + separatedTime[1] + "/" + separatedTime[0].substring(2);
                mDisposable.add(mViewModel.getTasksByDate(dueDate)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(tasks -> {
                            TaskListAdapter tt = new TaskListAdapter(getActivity(), tasks, mViewModel);
                            simpleList.setAdapter(tt);
                        }, throwable -> Log.e("Error in Calendar activity", "Unable to load task", throwable)));

            }
        });
        setChangeMonthButtons(view);

        title = view.findViewById(R.id.title);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

        selected_date = view.findViewById(R.id.selected_date);

        Locale locale = Locale.getDefault();
        selected_date.setText(month.get(Calendar.DATE) + " " + month.getDisplayName(Calendar.MONTH, Calendar.LONG, locale) + " " + month.get(Calendar.YEAR));

        // Subscribe to the emissions of the user name from the view model.
        // Update the user name text view, at every onNext emission.
        // In case of error, log the exception.
        mDisposable.add(mViewModel.getTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tasks -> {
                    TaskListAdapter tt = new TaskListAdapter(getActivity(), tasks, mViewModel);
                    simpleList.setAdapter(tt);
                }, throwable -> Log.e("Error in Calendar activity", "Unable to load task", throwable)));
        return view;
    }

    private void setChangeMonthButtons(View view) {
        ImageView previous = view.findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        ImageView next = view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();

            }
        });
    }

    protected void showToast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();

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

    public void refreshCalendar() {
        adapter.refreshDays();
        adapter.notifyDataSetChanged();
        updateEvents();


        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

        selected_date.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
    }

    public void updateEvents() {
        mDisposable.add(mViewModel.getTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tasklist -> {
                    for (Task temp:tasklist) {
                        items.add(temp.getDueDate());
                    }
                    adapter.setItems(items);
                    adapter.notifyDataSetChanged();
                }, throwable -> Log.e("Error in Calendar activity", "Unable to load task date", throwable)));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // clear all the subscriptions
        mDisposable.clear();

    }
}
