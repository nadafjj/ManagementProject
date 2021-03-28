package com.example.managementdna.TaskAndCost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.managementdna.Project.ProjectAdapter;
import com.example.managementdna.Project.ProjectDetails;
import com.example.managementdna.Project.ProjectList;
import com.example.managementdna.Project.ProjectModel;
import com.example.managementdna.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class projectTaskandCost extends AppCompatActivity {

    Button BackButton;
    ListView Task_List;
    List<TaskModel> arrayList=new ArrayList<>();
    //
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    ///
    String userID;
    String projectName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_taskand_cost);

        BackButton= findViewById(R.id.BackButton);

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(projectTaskandCost.this, ProjectList.class));
                finish();
            }
        });
        //////


        final TaskAdapter adapter=new TaskAdapter(projectTaskandCost.this, arrayList);
        Task_List=(ListView)findViewById(R.id.TaskList);
        Task_List.setAdapter(adapter);


        userID = fAuth.getCurrentUser().getUid();
        projectName="";

        Bundle extras = getIntent().getExtras();
        if (extras!=null)
            projectName = extras.getString("Project_Id");
        if (projectName==""){
            Toast.makeText(projectTaskandCost.this, "Something went wrong reload the app", Toast.LENGTH_LONG).show();
        }


        db.collection("users").document(userID).collection("Projects").document(projectName).collection("Tasks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Log.d("Task "," document "+ document.get("TaskCost"));
                            arrayList.add(new TaskModel(document.get("TaskTitle").toString(), document.get("TaskStartDate").toString(),  document.get("TaskAssignedResources").toString(), document.get("TaskDuration").toString(), document.get("TaskCost").toString()));
                            adapter.notifyDataSetChanged();
                    }
                }
            }


        });


    }

}