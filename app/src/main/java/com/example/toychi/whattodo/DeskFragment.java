package com.example.toychi.whattodo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

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
            LinearLayout task = new LinearLayout(getActivity());
            task.setOrientation(LinearLayout.VERTICAL);

            TextView t = new TextView(getActivity());
            t.setText("Task 1");
            t.setId(runningTask);
            task.setPadding(0,15,0,15);
            task.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent SubTaskView = new Intent(getActivity(), com.example.toychi.whattodo.SubTaskView.class);
                    startActivity(SubTaskView);
                }
            });
            task.addView(t);

            //Progress bar
            RoundCornerProgressBar progressBar = new RoundCornerProgressBar(getActivity(),null);
            progressBar.setMax(5);
            progressBar.setProgress((1.0f*runningTask));
            task.addView(progressBar);
            taskBox.addView(task);
        }
        return view;
    }

}
