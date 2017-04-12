package com.theironyard.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "spaces")
public class RehearsalSpace implements HasId{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    String id;

    @Column
    private String name;

    @Column
    private String location;

    @Column
    private String costPerHour;

    @Column
    private String squareFeet;


    @Column(length=2000)
    private ArrayList<String> amenities;

    @Column(length=2000)
    private ArrayList<String> availableEquipment;

    @Column
    private String spaceHostName;

    @Column
    private String hostEmail;

    @Column
    private String hostPhone;

    @Column
    private String featured;

    @Column(length=2000)
    private String description;

    @Column(length=2000)
    private String rules;

    @ManyToOne
    User user;

    public RehearsalSpace() {
    }

    public RehearsalSpace(String name, String location, String costPerHour,
                          String squareFeet, ArrayList<String> amenities, ArrayList<String> availableEquipment,
                          String spaceHostName, String hostEmail, String hostPhone, String featured,
                          String description, String rules) {
        this.name = name;
        this.location = location;
        this.costPerHour = costPerHour;
        this.squareFeet = squareFeet;
        this.amenities = amenities;
        this.availableEquipment = availableEquipment;
        this.spaceHostName = spaceHostName;
        this.hostEmail = hostEmail;
        this.hostPhone = hostPhone;
        this.featured = featured;
        this.description = description;
        this.rules = rules;
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

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public String getSpaceHostName() {
        return spaceHostName;
    }

    public void setSpaceHostName(String spaceHostName) {
        this.spaceHostName = spaceHostName;
    }

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }

    public String getHostPhone() {
        return hostPhone;
    }

    public void setHostPhone(String hostPhone) {
        this.hostPhone = hostPhone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCostPerHour() {
        return costPerHour;
    }

    public void setCostPerHour(String costPerHour) {
        this.costPerHour = costPerHour;
    }

    public String getSquareFeet() {
        return squareFeet;
    }

    public void setSquareFeet(String squareFeet) {
        this.squareFeet = squareFeet;
    }

    public ArrayList<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(ArrayList<String> amenities) {
        this.amenities = amenities;
    }

    public ArrayList<String> getAvailableEquipment() {
        return availableEquipment;
    }

    public void setAvailableEquipment(ArrayList<String> availableEquipment) {
        this.availableEquipment = availableEquipment;
    }
}