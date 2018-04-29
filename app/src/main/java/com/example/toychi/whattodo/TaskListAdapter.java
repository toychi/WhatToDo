package com.example.toychi.whattodo;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.toychi.whattodo.persistence.Subtask;
import com.example.toychi.whattodo.persistence.Task;
import com.example.toychi.whattodo.ui.SubtaskViewModel;
import com.example.toychi.whattodo.ui.SubtaskViewModelFactory;
import com.example.toychi.whattodo.ui.TaskViewModel;
import com.example.toychi.whattodo.ui.TaskViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TaskListAdapter extends BaseAdapter {
    private static final String TAG = TaskListAdapter.class.getSimpleName();
    AlertDialog DelSubtaskDialog;

    Context context;
    ArrayList<String> taskList;
    String[] item = new String[]{"Wireless Project", "HCI Project", "DataSci Project", "Essay", "TRX", "Security"};
    LayoutInflater inflter;
    private boolean[] mChecked;
    ArrayList tasks;

    private int type = 0; // Task

    // (Subtask) Room Database
    private SubtaskViewModel sViewModel;
    private final CompositeDisposable sDisposable = new CompositeDisposable();

    // (Task) Room Database
    private TaskViewModel mViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();


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
        this.tasks = new ArrayList<Subtask>();
        this.taskList = new ArrayList<String>();
        for (int i = 0; i < item.size(); i++) {
            this.tasks.add(item.get(i));
            this.taskList.add(item.get(i).getSubtaskName());
            if (item.get(i).getComplete() == 0) {
                mItemChecked[i] = false;
            } else {
                mItemChecked[i] = true;
            }
        }
        // (Subtask) Room Database
        sViewModel = viewModel;
        this.type = type;
    }

    public TaskListAdapter(Context appContext, ArrayList<Task> item, TaskViewModel viewModel) {
        this.context = appContext;
        this.tasks = new ArrayList<Task>();
        this.taskList = new ArrayList<String>();
        for (int i = 0; i < item.size(); i++) {
            this.tasks.add(item.get(i));
            this.taskList.add(item.get(i).getTaskName());
            if (item.get(i).getComplete() == 0) {
                mItemChecked[i] = false;
            } else {
                mItemChecked[i] = true;
            }
        }
        // (Task) Room Database
        mViewModel = viewModel;
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
        Object temp;
        if (type == 0) {
            temp = (Task) tasks.get(i);
        } else {
            temp = (Subtask) tasks.get(i);
        }
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
        if (type == 0) {
            holder.tv.setText(((Task) tasks.get(i)).getTaskName());
        } else {
            holder.tv.setText(((Subtask) tasks.get(i)).getSubtaskName());
        }
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
                        if (type == 0) {
                            mDisposable.add(mViewModel.updateTask(((Task) temp).getTid(), ((Task) temp).getCourse_id(), ((Task) temp).getTaskName(),
                                    ((Task) temp).getTaskDescription(), ((Task) temp).getDueDate(), ((Task) temp).getDueTime(), complete)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                            },
                                            throwable -> Log.e(TAG, "Unable to update task", throwable)));
                        } else {
                            sDisposable.add(sViewModel.updateTask(((Subtask) temp).getStid(), ((Subtask) temp).getTask_id(),
                                    ((Subtask) temp).getSubtaskName(), complete)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                            },
                                            throwable -> Log.e(TAG, "Unable to update subtask", throwable)));
                        }
                    }
                });
        
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longclick();
                return true;
            }
            private void longclick() {
                AlertDialog.Builder DelSubtaskDialogBuilder = new AlertDialog.Builder(context);
                DelSubtaskDialogBuilder.setTitle("Are you sure to delete this subtask?");
                // In case they want to delete subtask
                DelSubtaskDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }});
                // In case they don't want to delete subtask
                DelSubtaskDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                // Display a dialog
                DelSubtaskDialog = DelSubtaskDialogBuilder.create();
                DelSubtaskDialog.show();

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

