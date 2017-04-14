package com.theironyard.google_geocode_entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;


@JsonIgnoreProperties (ignoreUnknown = true)
public class Geocode {
    public ArrayList<Result> results;


    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public String getLat() {
        return results.get(0).geometry.getLocation().getLat();
    }

    public String getLng() {
        return results.get(0).geometry.getLocation().getLng();
    }
}
