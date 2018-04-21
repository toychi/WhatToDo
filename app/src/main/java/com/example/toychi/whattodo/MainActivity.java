package com.example.toychi.whattodo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import org.xmlpull.v1.XmlPullParser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        TextView textdate = findViewById(R.id.textdate);
        textdate.setText(formattedDate);

        //Add Task to taskbox
        LinearLayout taskBox = findViewById(R.id.taskbox);
        int totalTask = 3;

        for(int runningTask=0; runningTask<totalTask; runningTask++){
            TextView t = new TextView(this);
            t.setText("Task 1");
            t.setId(runningTask);

            //progressbar.setMax(5);
            //progressbar.setProgress((1.0f*runningTask));
            taskBox.addView(t);

            //Progress bar
            /*LinearLayout progressBar = new LinearLayout(this);
            progressBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,50));
            progressBar.setOrientation(LinearLayout.HORIZONTAL);
            progressBar.setBackgroundColor(Color.GRAY);
            int totalSub = runningTask+2;
            int subWidth = 700 / totalSub;
            int completeSub = runningTask;


            for(int i=0; i<completeSub+1; i++){
                TextView s = new TextView(this);
                s.setWidth(subWidth);
                s.setHeight(50);
                s.setBackgroundColor(Color.RED);
                progressBar.addView(s);
            }
            */

            RoundCornerProgressBar progressBar = new RoundCornerProgressBar(this, null);
            progressBar.setMax(5);
            progressBar.setProgress(3);
            progressBar.setPadding(10,10,10,10);

            taskBox.addView(progressBar);
        }
    }


    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
}