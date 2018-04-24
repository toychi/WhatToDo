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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DeskFragment extends Fragment {

    public ProgressListAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ProgressListAdapter(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_desk, container, false);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        String formattedDate = df.format(c);
        TextView textdate = view.findViewById(R.id.textdate);
        textdate.setText(formattedDate);

        /*

        //Add Task to taskbox
        LinearLayout taskBox = view.findViewById(R.id.taskbox);
        int totalTask = 3;
        for(int runningTask=0; runningTask<totalTask; runningTask++){
            LinearLayout task = new LinearLayout(getActivity());
            task.setOrientation(LinearLayout.VERTICAL);

            TextView t = new TextView(getActivity());
            t.setText("Task " + runningTask);
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
            progressBar.setLayoutParams(new RoundCornerProgressBar.LayoutParams(RoundCornerProgressBar.LayoutParams.MATCH_PARENT, 100));
            progressBar.setMax(5);
            progressBar.setPadding(1,1,1,1);
            progressBar.setProgressColor(Color.parseColor("#81d4fa"));
            progressBar.setProgress((1.0f*runningTask));
            task.addView(progressBar);
            taskBox.addView(task);

        }

        */

        // Progress Bar Adapter
        ListView listView = view.findViewById(R.id.taskbox);
        listView.setAdapter(adapter);
        listView.setDivider(null);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent SubTaskView = new Intent(getActivity(), com.example.toychi.whattodo.SubTaskView.class);
                startActivity(SubTaskView);
            }
        });

        return view;
    }

}
