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
import com.example.managementdna.TaskAndCost.projectTaskandCost;
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
    int day, month, year,myMonth;
    boolean SorEdate;
    Calendar calendarCheckStart,calendarCheckEnd;
    String StartCalender, EndCalender;
    DatePickerDialog datePickerDialog;
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

        StartCalender="";
        EndCalender="";
        calendarCheckStart= Calendar.getInstance();
        calendarCheckEnd = Calendar.getInstance();


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
                Log.d("Check date","Enter");
                pName= pName1.getText().toString().trim();
                pDescribtion = pDescribtion1.getText().toString();
                pGoals = pGoals1.getText().toString();
                pStartDate = pStartDate1.getText().toString();
                pEndDate = pEndDate1.getText().toString();
                pManager = pManager1.getText().toString();
                //Check if there is an empty field
                if (pName.isEmpty()){
                    Toast.makeText(ProjectDetails.this, "Fill project name ", Toast.LENGTH_LONG).show();
                    return;
                }
                if (pDescribtion.isEmpty()){
                    Toast.makeText(ProjectDetails.this, "Fill project description ", Toast.LENGTH_LONG).show();
                    return;
                }
                if (pGoals.isEmpty()){
                    Toast.makeText(ProjectDetails.this, "Fill project goal ", Toast.LENGTH_LONG).show();
                    return;
                }
                if (pStartDate.isEmpty()){
                    Toast.makeText(ProjectDetails.this, "Fill project start date ", Toast.LENGTH_LONG).show();
                    return;
                }
                if (pEndDate.isEmpty()){
                    Toast.makeText(ProjectDetails.this, "Fill project end date ", Toast.LENGTH_LONG).show();
                    return;
                }
                if (pManager.isEmpty()){
                    Toast.makeText(ProjectDetails.this, "Fill project manager name ", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.d("Check date"," "+ calendarCheckStart.get(Calendar.YEAR)+" "+calendarCheckEnd.get(Calendar.YEAR));
                if (calendarCheckStart.get(Calendar.YEAR)>calendarCheckEnd.get(Calendar.YEAR)||calendarCheckStart.get(Calendar.MONTH)>calendarCheckEnd.get(Calendar.MONTH)||calendarCheckStart.get(Calendar.DAY_OF_MONTH)>calendarCheckEnd.get(Calendar.DAY_OF_MONTH)){
                    Toast.makeText(ProjectDetails.this, "Please fill dates correctly", Toast.LENGTH_LONG).show();
                    return;
                }

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
                        Intent intentTo = new Intent(ProjectDetails.this, ProjectList.class);
                        startActivity(intentTo);
                        finish();
                    }
                });//end Oncomplete listener

            }//onclick
        });//end on click listener
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        myMonth = month+1;

if (SorEdate){
    calendarCheckStart.set(Calendar.YEAR, year);
    calendarCheckStart.set(Calendar.MONTH, month);
    calendarCheckStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);


    StartCalender = year+"/"+month+"/"+dayOfMonth;
    pStartDate1.setText(year+"/"+myMonth+"/"+dayOfMonth);
}else{
    calendarCheckEnd.set(Calendar.YEAR, year);
    calendarCheckEnd.set(Calendar.MONTH, month);
    calendarCheckEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    EndCalender = year+"/"+month+"/"+dayOfMonth;
    pEndDate1.setText(year+"/"+myMonth+"/"+dayOfMonth);

        }


    }


}