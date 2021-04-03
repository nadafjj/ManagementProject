package com.example.managementdna.TaskAndCost;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.managementdna.Project.ProjectDetails;
import com.example.managementdna.Project.ProjectList;
import com.example.managementdna.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddNewTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    EditText tTitle, tST, tDuration, tRes, tCost;
    private Button addconfirm, backToTasks;
    int day, month, year,myMonth;
    DatePickerDialog datePickerDialog;
    boolean SorEdate; /// no need
    Calendar calendarCheckStart; //no need
    String StartCalender;
    private String Title, ST, Duration, Res, Cost, userID, ProjectID;


    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);

        tTitle = findViewById(R.id.tTitle);
        tST = findViewById(R.id.tST);
        tDuration = findViewById(R.id.tDuration);
        tRes = findViewById(R.id.tRes);
        tCost = findViewById(R.id.tCost);
        addconfirm = findViewById(R.id.addconfirm);
        backToTasks = findViewById(R.id.backToTasks);

        StartCalender="";
        calendarCheckStart= Calendar.getInstance();

        userID = fAuth.getCurrentUser().getUid();
        Bundle extras = getIntent().getExtras();
        ProjectID="";
        if (extras!=null)
            ProjectID = extras.getString("Project_Id");
        if (ProjectID==""){
            Toast.makeText(AddNewTask.this, "AddNewTask Something went wrong reload the app", Toast.LENGTH_LONG).show();
        }

       /* backToTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddNewTask.this, projectTaskandCost.class));
                finish();
            }
        });*/

        tST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AddNewTask.this,AddNewTask.this,year, month,day);
                datePickerDialog.show();
                SorEdate = true;
            }
        });



        addconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Check date","addconfirm");

                Title= tTitle.getText().toString().trim();
                ST = tST.getText().toString();
                Duration = tDuration.getText().toString();
                Res = tRes.getText().toString();
                Cost = tCost.getText().toString();

                //Check if there is an empty field
                if (Title.isEmpty()){
                    Toast.makeText(AddNewTask.this, "Fill task Title ", Toast.LENGTH_LONG).show();
                    return;
                }
                if (ST.isEmpty()){
                    Toast.makeText(AddNewTask.this, "Fill task Start Date ", Toast.LENGTH_LONG).show();
                    return;
                }
                if (Duration.isEmpty()){
                    Toast.makeText(AddNewTask.this, "Fill task Duration ", Toast.LENGTH_LONG).show();
                    return;
                }
                if (Res.isEmpty()){
                    Toast.makeText(AddNewTask.this, "Fill task Assigned Resources ", Toast.LENGTH_LONG).show();
                    return;
                }
                if (Cost.isEmpty()){
                    Toast.makeText(AddNewTask.this, "Fill task Cost ", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!isDouble(Cost)){
                    Toast.makeText(AddNewTask.this, "Cost should be number ", Toast.LENGTH_LONG).show();
                    return;
                }




                //asynchronously update doc, create the document if missing
                Map<String, Object> update = new HashMap<>();
                update.put("capital", true);

               /* userID = fAuth.getCurrentUser().getUid();
                Bundle extras = getIntent().getExtras();
                ProjectID="";
                if (extras!=null)
                    ProjectID = extras.getString("Project_Id");
                if (ProjectID==""){
                    Toast.makeText(AddNewTask.this, "AddNewTask Something went wrong reload the app", Toast.LENGTH_LONG).show();
                }*/
                DocumentReference documentrefReference = db.collection("users").document(userID).collection("Projects").document(ProjectID).collection("Tasks").document();
                //store data
                Map<String, Object> Task = new HashMap<>();
                Task.put("TaskTitle", Title);
                Task.put("TaskDuration", Duration);
                Task.put("TaskAssignedResources", Res);
                Task.put("TaskStartDate", StartCalender);
                Task.put("TaskCost", Cost);

                //check the add if it's success or not
                documentrefReference.set(Task).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AddNewTask.this, "Task added successfully", Toast.LENGTH_LONG).show();
                        tTitle.setText("");
                        tST.setText("");
                        tDuration.setText("");
                        tRes.setText("");
                        tCost.setText("");
                        Intent intentTo = new Intent(AddNewTask.this, projectTaskandCost.class);
                        intentTo.putExtra("Project_Id", ProjectID);
                        startActivity(intentTo);
                        finish();
                    }
                });//end Oncomplete listener




            }//onclick
        });//end on click listener


        backToTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTo = new Intent(AddNewTask.this, projectTaskandCost.class);
                intentTo.putExtra("Project_Id", ProjectID);
                startActivity(intentTo);
                finish();

            }
        });

    }

    boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        myMonth = month+1;

        calendarCheckStart.set(Calendar.YEAR, year);
        calendarCheckStart.set(Calendar.MONTH, month);
        calendarCheckStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        StartCalender = year+"/"+month+"/"+dayOfMonth;
        tST.setText(year+"/"+myMonth+"/"+dayOfMonth);

    }
}
