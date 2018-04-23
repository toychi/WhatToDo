package com.example.toychi.whattodo;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DeskFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_desk, container, false);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        TextView textdate = view.findViewById(R.id.textdate);
        textdate.setText(formattedDate);

        //Add Task to taskbox
        LinearLayout taskBox = view.findViewById(R.id.taskbox);
        int totalTask = 3;
        for(int runningTask=0; runningTask<totalTask; runningTask++){
            TextView t = new TextView(getActivity());
            t.setText("Task 1");
            t.setId(runningTask);

            //progressbar.setMax(5);
            //progressbar.setProgress((1.0f*runningTask));
            taskBox.addView(t);

            //Progress bar
            LinearLayout progressBar = new LinearLayout(getActivity());
            progressBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,50));
            progressBar.setOrientation(LinearLayout.HORIZONTAL);
            progressBar.setBackgroundColor(Color.GRAY);
            int totalSub = runningTask+2;
            int subWidth = 700 / totalSub;
            int completeSub = runningTask;


            for(int i=0; i<completeSub+1; i++){
                TextView s = new TextView(getActivity());
                s.setWidth(subWidth);
                s.setHeight(50);
                s.setBackgroundColor(Color.RED);
                progressBar.addView(s);
            }
            taskBox.addView(progressBar);
        }

        return view;
    }

}
