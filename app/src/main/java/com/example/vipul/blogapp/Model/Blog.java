package com.example.vipul.blogapp.Model;

public class Blog {
    public String title1;
    public String decription;
    public String image1;
    public String timestamp;
    public String userid;
    public String userName;
    public String userMail;
    public String postId;

    public Blog() {
    }

    public Blog(String title1, String decription, String image1, String timestamp, String userid, String userName, String userMail, String postId) {
        this.title1 = title1;
        this.decription = decription;
        this.image1 = image1;
        this.timestamp = timestamp;
        this.userid = userid;
        this.userName = userName;
        this.userMail = userMail;
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
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
