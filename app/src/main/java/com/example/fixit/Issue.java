package com.example.fixit;

import java.util.Date;

public class Issue {

    String issueID;
    Date date;
    String title;
    String description;
    Integer fixvotes;
    Location location;

    public Issue(){}

    public Issue(String title, String key, String description, Location location){
        this.title = title;
        this.location = location;
        this.fixvotes = 0;
        this.date = new Date();
        this.description = description;
        this.issueID = key;
    }

    public Integer getFixvotes() {
        return fixvotes;
    }

    public void setFixvotes(Integer fixvotes) {
        this.fixvotes = fixvotes;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIssueID() {
        return issueID;
    }

    public void setIssueID(String issueID) {
        this.issueID = issueID;
    }


    public Double getLatitude() {
        return location.getLatitude();
    }


    public Double getLongitude() {
        return location.getLongitude();
    }

}