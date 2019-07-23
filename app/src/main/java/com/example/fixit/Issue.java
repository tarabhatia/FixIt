package com.example.fixit;

import java.util.Date;

public class Issue {

//    User user;
//    String description;
    String issueID;
    Date date;
    String title;
    String description;
    Integer fixvotes;
    Location location;
//    Double latitude;
//    Double longitude;


    public Issue(String title, String key, String description, Location location){
        this.title = title;
        this.location = location;
        this.fixvotes = 0;
        this.date = new Date();
        this.description = description;
        this.issueID = key;
    }

    //    public Issue(String description, String title, Double latitude, Double longitude){
//        user = null;
//        this.title = title;
//        this.description = description;
//        this.latitude = latitude;
//        this.longitude = longitude;
//    }

    public Issue(){}

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
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }


    public Double getLatitude() {
        return location.getLatitude();
    }

//    public void setLatitude(Double latitude) {
//        this.location. = latitude;
//    }

    public Double getLongitude() {
        return location.getLongitude();
    }

//    public void setLongitude(Double longitude) {
//        this.longitude = longitude;
//    }
}