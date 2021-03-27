package com.example.managementdna.Project;

import androidx.appcompat.app.AppCompatActivity;

public class ProjectModel extends AppCompatActivity {

    private String pName,pDescribtion, pGoals, pStartDate, pEndDate, pManager, projectId;



    public ProjectModel(String pName, String pDescribtion, String pGoals, String pStartDate, String pEndDate, String pManager, String projectId){
        this.pName=pName;
        this.pDescribtion=pDescribtion;
        this.pGoals=pGoals;
        this.pStartDate=pStartDate;
        this.pEndDate=pEndDate;
        this.pManager=pManager;
        this.projectId = projectId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpDescribtion() {
        return pDescribtion;
    }

    public void setpDescribtion(String pDescribtion) {
        this.pDescribtion = pDescribtion;
    }

    public String getpGoals() {
        return pGoals;
    }

    public void setpGoals(String pGoals) {
        this.pGoals = pGoals;
    }

    public String getpStartDate() {
        return pStartDate;
    }

    public void setpStartDate(String pStartDate) {
        this.pStartDate = pStartDate;
    }

    public String getpEndDate() {
        return pEndDate;
    }

    public void setpEndDate(String pEndDate) {
        this.pEndDate = pEndDate;
    }

    public String getpManager() {
        return pManager;
    }

    public void setpManager(String pManager) {
        this.pManager = pManager;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
