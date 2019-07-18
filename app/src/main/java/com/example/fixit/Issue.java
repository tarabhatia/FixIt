package com.example.fixit;

public class Issue {

    User user;
    String category;
    String description;
    String issueID;

    public Issue(){}

    public Issue(String description, String issueID){
        user = null;
        category = null;
        this.description = description;
        this.issueID = issueID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
}
