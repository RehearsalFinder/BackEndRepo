package com.theironyard.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "availabilities")
public class Availability implements HasId {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    String id;

    @Column
    private String date;

    @Column
    @JsonProperty("start-time")
    private String startTime;

    @Column
    @JsonProperty("end-time")
    private String endTime;

    @ManyToOne
    @JsonProperty("claimed-by")
    private User claimedBy;

    @ManyToOne
    @JsonProperty("belongs-to")
    private RehearsalSpace belongsTo;


    public Availability() {
    }

    public Availability(String date, String startTime, String endTime, User claimedBy, RehearsalSpace belongsTo) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.claimedBy = claimedBy;
        this.belongsTo = belongsTo;

    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public User getClaimedBy() {
        return claimedBy;
    }

    public void setClaimedBy(User claimedBy) {
        this.claimedBy = claimedBy;
    }

    public RehearsalSpace getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(RehearsalSpace belongsTo) {
        this.belongsTo = belongsTo;
    }
}
