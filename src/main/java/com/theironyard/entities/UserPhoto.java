//package com.theironyard.entities;
//
//import javax.persistence.*;
//
//
//@Entity
//public class UserPhoto {
//
//    @Id
//    @GeneratedValue
//    int id;
//
//    @Column(nullable = false)
//    String filename;
//
//
//
//    User user;
//
//    public UserPhoto() {
//    }
//
//    public UserPhoto(String filename, User user) {
//        this.filename = filename;
//        this.user = user;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getFilename() {
//        return filename;
//    }
//
//    public void setFilename(String filename) {
//        this.filename = filename;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//}
