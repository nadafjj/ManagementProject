package com.example.managementdna.Project;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.managementdna.R;
import com.example.managementdna.TaskAndCost.AddNewTask;
import com.example.managementdna.TaskAndCost.projectTaskandCost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;


public class ProjectAdapter extends ArrayAdapter<ProjectModel> {
    public static final String TAG = "TAG";
    private final Activity context;
    TextView del;//
    StorageReference profileRef;//
    String itemID;//
    StorageReference storageRef;//
    FirebaseFirestore fStore;//

    private final List<ProjectModel> arrayList;
    FirebaseAuth fAuth;

    public ProjectAdapter(@NonNull Activity context, @NonNull List<ProjectModel> arrayList) {
        super(context, R.layout.projectitem, arrayList);
        this.arrayList=arrayList;
        Log.d(TAG,  "SIZE ADAPTER => " +arrayList.size());
        this.context=context;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        fAuth = FirebaseAuth.getInstance();
        View rowView = inflater.inflate(R.layout.projectitem, null, true);

        TextView Pname =  rowView.findViewById(R.id.Pname);
        TextView Pdescription =  rowView.findViewById(R.id.Pdescription);


        Pname.setText(arrayList.get(position).getpName());
        Pdescription.setText(arrayList.get(position).getpDescribtion());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, projectTaskandCost.class);
                intent.putExtra("Project_Id", arrayList.get(position).getProjectId());
                context.startActivity(intent);



            }
        });

        return rowView;

    };




}