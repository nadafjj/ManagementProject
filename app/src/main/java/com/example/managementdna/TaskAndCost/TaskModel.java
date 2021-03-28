package com.example.managementdna.TaskAndCost;

public class TaskModel {
    private String title , startDate,assignedResources,duration,cost ;

    public TaskModel(String title, String startDate, String assignedResources, String duration, String cost) {
        this.title = title;
        this.startDate = startDate;
        this.assignedResources = assignedResources;
        this.duration = duration;
        this.cost = cost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getAssignedResources() {
        return assignedResources;
    }

    public void setAssignedResources(String assignedResources) {
        this.assignedResources = assignedResources;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
