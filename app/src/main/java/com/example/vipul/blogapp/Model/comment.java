package com.example.vipul.blogapp.Model;

public class comment {

    public String comment;
    public String name;

    public comment() {
    }

    public comment(String comment, String name) {
        this.comment = comment;
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
