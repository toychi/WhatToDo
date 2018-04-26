package com.example.toychi.whattodo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.util.ArrayList;

public class ProgressListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflter;
    ArrayList<String> items = new ArrayList<String>();
    ArrayList<Integer> times = new ArrayList<Integer>();
    ArrayList<Integer> bars = new ArrayList<Integer>();
    String[] dummy = new String[]{"Wireless Project","HCI Project","DataSci Project","Essay","TRX","Security"};
    Integer[] dummy2 = new Integer[]{4,7,12,15,16,20};
    Integer[] dummy3 = new Integer[]{70,90,20,40,50,10};

    public ProgressListAdapter(Context appContext){
        this.context = appContext;
        inflter = (LayoutInflater.from(appContext));
        for (String temp: dummy) {
            items.add(temp);
        }
        for (Integer temp: dummy2) {
            times.add(temp);
        }
        for (Integer temp: dummy3) {
            bars.add(temp);
        }
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.progress_task_item, null);
        TextView name = (TextView) view.findViewById(R.id.taskName);
        TextView time = (TextView) view.findViewById(R.id.timeLeft);
        RoundCornerProgressBar bar = (RoundCornerProgressBar) view.findViewById(R.id.progressBar);
        name.setText(this.items.get(i));
        time.setText(Integer.toString(this.times.get(i)) + " days left");
        bar.setMax(100);
        bar.setPadding(1,1,1,1);
        bar.setProgressColor(Color.parseColor("#81d4fa"));
        bar.setProgress(1.0f*this.bars.get(i));
        return view;
    }
}
