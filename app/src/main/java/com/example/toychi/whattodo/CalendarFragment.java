package com.example.toychi.whattodo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    public Calendar month; // Calendar instances
    public CalendarAdapter adapter; // Adapter instance
    public ListView simpleList; // Task list

    DateFormatSymbols dfs = new DateFormatSymbols();
    final private String[] months = dfs.getMonths(); // Months strings

    public TextView selected_date;

    public TextView title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        month = Calendar.getInstance();
        adapter = new CalendarAdapter(getActivity(),(GregorianCalendar) month);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_calendar, container, false);

        GridView gridview = view.findViewById(R.id.gridview);
        gridview.setAdapter(adapter);
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
        setChangeMonthButtons(view);

        title = view.findViewById(R.id.title);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

        selected_date = view.findViewById(R.id.selected_date);

        Locale locale = Locale.getDefault();
        selected_date.setText(month.get(Calendar.DATE) + " " + month.getDisplayName(Calendar.MONTH, Calendar.LONG, locale) + " " + month.get(Calendar.YEAR));

        simpleList = (ListView) view.findViewById(R.id.list);
        TaskListAdapter taskListAdapter = new TaskListAdapter(getContext(),new String[]{"a"});
        simpleList.setAdapter(taskListAdapter);

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
        //handler.post(calendarUpdater); // generate some calendar items

        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

        selected_date.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
    }
}
