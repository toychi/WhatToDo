package com.example.toychi.whattodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

public class SubTaskView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_task_view);

        //Task nname
        TextView TaskNum = findViewById(R.id.TaskNum);
        TaskNum.setText("Task 1");
        //DueDate
        TextView DueDate = findViewById(R.id.DueDate);
        DueDate.setText("Due on:");
        //Progress bar
        RoundCornerProgressBar progressBar = findViewById(R.id.RoundPg);
        progressBar.setMax(7);
        progressBar.setProgress(3);
        //Percent
        TextView TaskPercent = findViewById(R.id.TaskPercent);
        String TaskPc = String.valueOf(Math.round(progressBar.getProgress()/progressBar.getMax()*100));
        TaskPercent.setText(TaskPc + "%");

    }


}
