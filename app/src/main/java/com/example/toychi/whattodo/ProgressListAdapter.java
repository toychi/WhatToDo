package com.example.toychi.whattodo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.toychi.whattodo.persistence.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ProgressListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflter;
    ArrayList<Integer> times = new ArrayList<Integer>();
    ArrayList<Integer> bars = new ArrayList<Integer>();
    ArrayList<Task> tasks;
    String[] dummy = new String[]{"Wireless Project","HCI Project","DataSci Project","Essay","TRX","Security"};
    Integer[] dummy2 = new Integer[]{4,7,12,15,16,20};
    Integer[] dummy3 = new Integer[]{70,90,20,40,50,10};

    public ProgressListAdapter(Context appContext, ArrayList<Task> tasks){
        this.context = appContext;
        this.tasks = tasks;
        inflter = (LayoutInflater.from(appContext));
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return tasks.get(i).getTid();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.progress_task_item, null);
        TextView name = (TextView) view.findViewById(R.id.taskName);
        TextView time = (TextView) view.findViewById(R.id.timeLeft);
        RoundCornerProgressBar bar = (RoundCornerProgressBar) view.findViewById(R.id.progressBar);
        name.setText(this.tasks.get(i).getTaskName());
        time.setText(Integer.toString(daysLeft(this.tasks.get(i).getDueDate())) + " days left");
        bar.setMax(100);
        bar.setPadding(1,1,1,1);
        bar.setProgressColor(Color.parseColor("#81d4fa"));
        bar.setProgress(50);
        return view;
    }

    public static int daysLeft(String dueDate) {
        int daysLeft = 0;
        int monthDiff;
        // Current date
        Calendar currDay = Calendar.getInstance();
        // Due date
        Calendar day = Calendar.getInstance();

        try {
            day.setTime(new SimpleDateFormat("dd/MM/yy").parse(dueDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(day.after(currDay)){
            monthDiff = day.get(Calendar.MONTH) - currDay.get(Calendar.MONTH);

            // Due date is on same month
            if (monthDiff == 0) {
                return day.get(Calendar.DAY_OF_MONTH) - currDay.get(Calendar.DAY_OF_MONTH);
            }
            // Due date in on more than one month
            if (monthDiff > 1) {
                Calendar btwMonth = Calendar.getInstance();
                for (int i = 1; i < monthDiff; i++) {
                    btwMonth.add(Calendar.MONTH, 1);
                    daysLeft += btwMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
                }
            }
            // Day before end of current month and day before due date of due month
            daysLeft += currDay.getActualMaximum(Calendar.DAY_OF_MONTH) - currDay.get(Calendar.DAY_OF_MONTH);
            daysLeft += day.get(Calendar.DAY_OF_MONTH) - day.getActualMinimum(Calendar.DAY_OF_MONTH);
        }
        return daysLeft;
    }
}
