package com.theironyard.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("street-address")
    private String streetAddress;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String zip;

    @Column
    @JsonProperty("cost-per-hour")
    private String costPerHour;

    @Column
    @JsonProperty("square-feet")
    private String squareFeet;


    @Column(length=3000)
    private ArrayList<String> amenities;

    @Column(length=3000)
    @JsonProperty("available-equipment")
    private ArrayList<String> availableEquipment;

    @Column
    @JsonProperty("space-host-name")
    private String spaceHostName;

    @Column
    @JsonProperty("host-email")
    private String hostEmail;

    @Column
    @JsonProperty("host-phone")
    private String hostPhone;

    @Column
    private String featured;

    @Column(length=3000)
    private String description;

    @Column(length=3000)
    private String rules;

    @Column
    private String coordinates;

    @ManyToOne
    User user;

    public RehearsalSpace() {
    }

    public RehearsalSpace(String name, String streetAddress, String costPerHour,
                          String squareFeet, ArrayList<String> amenities, ArrayList<String> availableEquipment,
                          String spaceHostName, String hostEmail, String hostPhone, String featured,
                          String description, String rules, String city, String state, String zip, String coordinates) {
        this.name = name;
        this.streetAddress = streetAddress;
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
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.coordinates = coordinates;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
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

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
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