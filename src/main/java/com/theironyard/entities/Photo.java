package com.theironyard.entities;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "profilePhoto")
public class Photo implements HasId {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    String id;

    @Column
    @JsonProperty("photo-url")
    String photoUrl;

    @OneToOne
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(String val) {
        this.id = val;
    }

    public void setPhotoUrl(String val) {
        this.photoUrl = val;
    }

    public String getId() {
        return this.id;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }
}