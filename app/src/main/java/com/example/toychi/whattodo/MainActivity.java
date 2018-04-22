package com.example.toychi.whattodo;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Date c = Calendar.getInstance().getTime();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


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
            LinearLayout progressBar = new LinearLayout(this);
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
            taskBox.addView(progressBar);
        }

        // Handle navigation click events
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        selectDrawerItem(menuItem);

                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_desk:
                fragmentClass = CalendarFragment.class;
                break;

            case R.id.nav_calendar:
                fragmentClass = CalendarFragment.class;
                break;
                
                /*
            case R.id.nav_completed:
                fragmentClass = ThirdFragment.class;
                break;
                */
            default:
                fragmentClass = CalendarFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}