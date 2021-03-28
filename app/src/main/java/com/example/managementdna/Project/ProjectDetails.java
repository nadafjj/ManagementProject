package com.example.managementdna.Project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.managementdna.R;
import com.example.managementdna.Register;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProjectDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private String pName,pDescribtion, pGoals, pStartDate, pEndDate, pManager, userID;
    int day, month, year;
    int myday, myMonth, myYear;
    boolean SorEdate;
    Calendar calendar;
    String StartCalender, EndCalender;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
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
        pStartDate1 = (EditText)findViewById(R.id.pStartDate1);
        pEndDate1 = (EditText)findViewById(R.id.pEndDate1);
        pManager1 = findViewById(R.id.pManager1);
        taskCostBtn = findViewById(R.id.taskCostBtn);
        backToMAin = findViewById(R.id.backToMAin);

        calendar=Calendar.getInstance();
        StartCalender="";
        EndCalender="";


        pStartDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(ProjectDetails.this,ProjectDetails.this,year, month,day);
                datePickerDialog.show();
                SorEdate = true;
            }
        });
        pEndDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(ProjectDetails.this,ProjectDetails.this,year, month,day);
                datePickerDialog.show();
                SorEdate = false;
            }
        });


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
                Project.put("Project_StartDate", StartCalender);
                Project.put("Project_EndDate", EndCalender);
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        myMonth = month+1;

if (SorEdate){
    StartCalender = year+"/"+month+"/"+dayOfMonth;
    pStartDate1.setText(year+"/"+myMonth+"/"+dayOfMonth);
}else{
    EndCalender = year+"/"+month+"/"+dayOfMonth;
    pEndDate1.setText(year+"/"+myMonth+"/"+dayOfMonth);
        }


    }


}