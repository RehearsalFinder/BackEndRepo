package com.theironyard.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
public class UserPhoto {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    int id;

    @Column(nullable = false)
    String filename;

    @Column
    String photoUrl;

    User user;

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public UserPhoto() {
    }

    public UserPhoto(String filename, User user) {
        this.filename = filename;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
