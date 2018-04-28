package com.example.toychi.whattodo;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.toychi.whattodo.ui.TaskViewModel;
import com.example.toychi.whattodo.ui.TaskViewModelFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DeskFragment extends Fragment {

    public ProgressListAdapter adapter;

    // Room Database
    private TaskViewModelFactory mViewModelFactory;
    private TaskViewModel mViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

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
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        String formattedDate = df.format(c);
        TextView textdate = view.findViewById(R.id.textdate);
        textdate.setText(formattedDate);

        // (Task) Room Database
        mViewModelFactory = Injection.provideTaskViewModelFactory(getActivity());
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(TaskViewModel.class);

        // Progress Bar Adapter
        ListView listView = view.findViewById(R.id.taskbox);
        listView.setDivider(null);
        mDisposable.add(mViewModel.getTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tasks -> {
                    adapter = new ProgressListAdapter(getActivity(), tasks);
                    listView.setAdapter(adapter);
                }, throwable -> Log.e("Error in Desk fragment", "Unable to load tasks", throwable)));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent SubTaskView = new Intent(getActivity(), com.example.toychi.whattodo.SubTaskView.class);
                long temp = adapter.getItemId(i);
                SubTaskView.putExtra("tid", (int) temp);
                startActivity(SubTaskView);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fabAddTask);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddTask = new Intent(getActivity(), AddTaskActivity.class);
                startActivity(AddTask);
            }
        });



        return view;
    }

}
