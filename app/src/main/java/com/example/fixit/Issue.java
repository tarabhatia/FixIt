package com.example.fixit;

import com.google.android.libraries.places.api.model.Place;

import java.util.Date;

public class Issue {

    User user;
    String description;
    String issueID;
    Date date;
    String title;
    Place location;

    public Issue(){}

    public Issue(String description, String title, Place location){
        user = null;
        this.title = title;
        this.description = description;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIssueID() {
        return issueID;
    }

    public void setIssueID(String issueID) {
        this.issueID = issueID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Place getLocation() {
        return location;
    }

    public void setLocation(Place location) {
        this.location = location;
    }
}