package com.theironyard.google_geocode_entities;


import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoLocation {
    @JsonProperty("lat")
   public String lat;
    @JsonProperty("lng")
    public String lng;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
