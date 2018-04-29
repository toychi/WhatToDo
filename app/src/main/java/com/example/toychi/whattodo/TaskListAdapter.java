package com.example.toychi.whattodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskListAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> taskList;
    String[] item = new String[]{"Wireless Project","HCI Project","DataSci Project","Essay","TRX","Security"};
    LayoutInflater inflter;

    public TaskListAdapter(Context appContext, ArrayList<String> item){
        this.context = appContext;
        this.taskList = item;
        inflter = (LayoutInflater.from(appContext));
    }

    @Override
    public int getCount() {
        return taskList.size();
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
        view = inflter.inflate(R.layout.task_item, null);
        TextView item = (TextView) view.findViewById(R.id.textView4);
        item.setText(this.taskList.get(i));
        return view;
    }
}

