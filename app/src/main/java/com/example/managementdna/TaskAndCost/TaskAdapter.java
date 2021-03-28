package com.example.managementdna.TaskAndCost;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.managementdna.R;
import com.example.managementdna.TaskAndCost.projectTaskandCost;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


public class TaskAdapter extends ArrayAdapter<TaskModel> {
    public static final String TAG = "TAG";
    private final Activity context;

    private final List<TaskModel> arrayList;
    FirebaseAuth fAuth;

    public TaskAdapter(@NonNull Activity context, @NonNull List<TaskModel> arrayList) {
        super(context, R.layout.activity_task_card, arrayList);
        this.arrayList=arrayList;
        Log.d(TAG,  "SIZE ADAPTER => " +arrayList.size());
        this.context=context;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        fAuth = FirebaseAuth.getInstance();
        View rowView = inflater.inflate(R.layout.activity_task_card, null, true);

        TextView taskTitle =  rowView.findViewById(R.id.taskTitle);
        TextView taskstartDate =  rowView.findViewById(R.id.taskstartDate);
        TextView taskDuration =  rowView.findViewById(R.id.taskDuration);
        TextView taskResorces =  rowView.findViewById(R.id.taskResorces);
        TextView taskcost =  rowView.findViewById(R.id.taskcost);


        taskTitle.setText(arrayList.get(position).getTitle());
        taskstartDate.setText(arrayList.get(position).getStartDate());
        taskDuration.setText(arrayList.get(position).getDuration());
        taskResorces.setText(arrayList.get(position).getAssignedResources());
        taskcost.setText(arrayList.get(position).getCost());



        return rowView;

    };


}