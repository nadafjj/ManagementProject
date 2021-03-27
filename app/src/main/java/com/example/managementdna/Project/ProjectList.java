package com.example.managementdna.Project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.managementdna.R;
import com.example.managementdna.Register;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProjectList extends AppCompatActivity {
    Button logout,addProject;
    ListView Project_List;
    List<ProjectModel> arrayList=new ArrayList<>();
    //
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    ///
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        Log.d("MainActivity"," Enter");
        logout = findViewById(R.id.logout);
        addProject = findViewById(R.id.addProject);
        getSupportActionBar().hide();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
                finish();
            }
        });
        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProjectList.this, ProjectDetails.class));
                finish();
            }
        });

        final ProjectAdapter adapter=new ProjectAdapter(ProjectList.this, arrayList);
        Project_List=(ListView)findViewById(R.id.ProjectList);
        Project_List.setAdapter(adapter);


        userID = fAuth.getCurrentUser().getUid();

        db.collection("users").document(userID).collection("Projects").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Log.d("ProjectList"," document "+ document.get("Project_Name"));
                        arrayList.add(new ProjectModel(document.get("Project_Name").toString(), document.get("Project_Describtion").toString(),  document.get("Project_Goals").toString(), document.get("Project_StartDate").toString(), document.get("Project_EndDate").toString(), document.get("Project_Manager").toString(), document.getId()));
                        adapter.notifyDataSetChanged();
                    }
                }
            }


        });

    }
}