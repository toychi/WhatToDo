package com.example.toychi.whattodo;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.toychi.whattodo.persistence.Subtask;
import com.example.toychi.whattodo.ui.SubtaskViewModel;
import com.example.toychi.whattodo.ui.SubtaskViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TaskListAdapter extends BaseAdapter {
    private static final String TAG = TaskListAdapter.class.getSimpleName();

    Context context;
    ArrayList<String> taskList;
    String[] item = new String[]{"Wireless Project", "HCI Project", "DataSci Project", "Essay", "TRX", "Security"};
    LayoutInflater inflter;
    private boolean[] mChecked;
    ArrayList<Subtask> subtasks;

    // (Subtask) Room Database
    private SubtaskViewModel sViewModel;
    private final CompositeDisposable sDisposable = new CompositeDisposable();


    private List<String> arrayList = new ArrayList<>(30);
    private boolean[] mItemChecked = new boolean[30];

    public TaskListAdapter(Context appContext, ArrayList<String> item) {
        this.context = appContext;
//        this.taskList = item;
//        inflter = (LayoutInflater.from(appContext));
//        mChecked = new boolean[item.size()];

        for (int i = 0; i < 30; i++) {
            arrayList.add("text: " + i);
            mItemChecked[i] = false;
        }
    }

    public TaskListAdapter(Context appContext, ArrayList<Subtask> item, int type, SubtaskViewModel viewModel) {
        this.context = appContext;
        this.subtasks = new ArrayList<Subtask>();
        this.taskList = new ArrayList<String>();
        for (int i = 0; i < item.size(); i++) {
            this.subtasks.add(item.get(i));
            this.taskList.add(item.get(i).getSubtaskName());
            System.out.println(this.taskList.get(i));
            if (item.get(i).getComplete() == 0) {
                mItemChecked[i] = false;
            } else {
                mItemChecked[i] = true;
            }
        }
        // (Subtask) Room Database
        sViewModel = viewModel;
    }

    @Override
    public int getCount() {
         return taskList.size();
//        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public boolean itemIsChecked(int position) {
        return mItemChecked[position];
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder;
        Subtask temp = subtasks.get(i);
        inflter = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflter.inflate(R.layout.task_item, null);
            holder = new ViewHolder();
            holder.tv = (TextView) view.findViewById(R.id.textView4);
            holder.cb = (CheckBox) view.findViewById(R.id.checkbox);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv.setText(temp.getSubtaskName());
        //Important to remove previous listener before calling setChecked
        holder.cb.setOnCheckedChangeListener(null);
        holder.cb.setChecked(mItemChecked[i]);
        holder.cb.setTag(i);
        holder.cb.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        mItemChecked[i] = isChecked;
                        int complete = isChecked ? 1 : 0;
                        sDisposable.add(sViewModel.updateTask(temp.getStid(), temp.getTask_id(), temp.getSubtaskName(), complete)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                        },
                                        throwable -> Log.e(TAG, "Unable to add subtask", throwable)));
                    }
                });
        return view;
    }

    private class ViewHolder {
        TextView tv;
        CheckBox cb;

    }

    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}

