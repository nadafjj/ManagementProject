package com.example.managementdna.Project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.managementdna.R;
import com.example.managementdna.Register;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProjectDetails extends AppCompatActivity {

    private String pName,pDescribtion, pGoals, pStartDate, pEndDate, pManager, userID;
    EditText pName1, pDescribtion1, pGoals1, pStartDate1, pEndDate1, pManager1;
    private Button taskCostBtn,backToMAin;
///
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
        pName1 = findViewById(R.id.pName1);
        pDescribtion1 = findViewById(R.id.pDescribtion1);
        pGoals1 = findViewById(R.id.pGoals1);
        pStartDate1 = findViewById(R.id.pStartDate1);
        pEndDate1 = findViewById(R.id.pEndDate1);
        pManager1 = findViewById(R.id.pManager1);
        taskCostBtn = findViewById(R.id.taskCostBtn);
        backToMAin = findViewById(R.id.backToMAin);

        backToMAin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProjectDetails.this, ProjectList.class));
                finish();
            }
        });



        taskCostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pName= pName1.getText().toString().trim();
                pDescribtion = pDescribtion1.getText().toString();
                pGoals = pGoals1.getText().toString();
                pStartDate = pStartDate1.getText().toString();
                pEndDate = pEndDate1.getText().toString();
                pManager = pManager1.getText().toString();
                Log.d("ProjectDetails","pName1"+pName);

                //asynchronously update doc, create the document if missing
                Map<String, Object> update = new HashMap<>();
                update.put("capital", true);

                 userID = fAuth.getCurrentUser().getUid();
                DocumentReference documentrefReference = db.collection("users").document(userID).collection("Projects").document();
                //store data
                Map<String, Object> Project = new HashMap<>();
                Project.put("Project_Name", pName);
                Project.put("Project_Describtion", pDescribtion);
                Project.put("Project_Goals", pGoals);
                Project.put("Project_StartDate", pStartDate);
                Project.put("Project_EndDate", pEndDate);
                Project.put("Project_Manager", pManager);
                //check the add if it's success or not
                documentrefReference.set(Project).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ProjectDetails.this, "Project Saved successfully", Toast.LENGTH_LONG).show();
                    }
                });//end Oncomplete listener

            }//onclick
        });//end on click listener
    }
}