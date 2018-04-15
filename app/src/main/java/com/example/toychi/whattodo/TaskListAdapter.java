package com.example.toychi.whattodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskListAdapter extends BaseAdapter {

    Context context;
    String[] item = new String[]{"Wireless Project","HCI Project","DataSci Project","Essay","TRX","Security"};
    LayoutInflater inflter;

    public TaskListAdapter(Context appContext, String[] item){
        this.context = appContext;
        inflter = (LayoutInflater.from(appContext));
    }

    @Override
    public int getCount() {
        return item.length;
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
        ImageView image = (ImageView) view.findViewById(R.id.imageView);
        image.setImageResource(R.drawable.ic_launcher_background);
        item.setText(this.item[i]);
        return view;
    }
}

