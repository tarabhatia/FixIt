package com.example.fixit;

import android.location.Geocoder;
import android.net.Uri;

import java.util.Date;
import java.util.Locale;

public class Issue {

    User user;
    String title;
    String description;
    Double lat;
    Double lon;
    String issueID;
    Date date;
    Uri issuePhoto;


    public Issue(){}



    public Issue(String title, String description, Double lat, Double lon, String issueID){
        user = null;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
        this.title = title;
        this.issueID = issueID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
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

//    public void getLat(String city) {
//        GeoPoint startGP = new GeoPoint(
//                (int) (Double.parseDouble(getCity()) * 1E6));
//        Double.toString((double)startGP.getLatitudeE6() / 1E6),
//                Double.toString((double)dest.getLongitudeE6() / 1E6)
//    }

}
