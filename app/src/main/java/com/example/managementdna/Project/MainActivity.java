package com.example.managementdna.Project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.managementdna.R;
import com.example.managementdna.Register;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button logout,addProject;
    ListView ProjectList;
    List<ProjectModel> arrayList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                startActivity(new Intent(MainActivity.this, ProjectDetails.class));
                finish();
            }
        });

        final ProjectAdapter adapter=new ProjectAdapter(MainActivity.this, arrayList);
        ProjectList=(ListView)findViewById(R.id.ProjectList);
        ProjectList.setAdapter(adapter);

/*
        arrayList.add(new BenModelBlock((String)document.getId(), count, (String)document.get("phoneNumber"),(String)document.get("userName"),(String)document.get("email")));
        adapter.notifyDataSetChanged();

 */

    }
}