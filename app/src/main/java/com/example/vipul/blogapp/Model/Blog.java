package com.example.vipul.blogapp.Model;

public class Blog {
    public String title1;
    public String decription;
    public String image1;
    public String timestamp;
    public String userid;

    public Blog() {
    }

    public Blog(String title1, String decription, String image1, String timestamp, String userid) {
        this.title1 = title1;
        this.decription = decription;
        this.image1 = image1;
        this.timestamp = timestamp;
        this.userid = userid;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
