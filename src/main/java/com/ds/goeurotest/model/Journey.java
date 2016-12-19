package com.ds.goeurotest.model;

/**
 * Created by dmitriisperanskii on 19/12/2016.
 */
public class Journey {

    public Journey(String fromLocation, String toLocation) {
        this.setFromLocation(fromLocation);
        this.setToLocation(toLocation);
    }

    private String fromLocation;
    private String toLocation;

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }
}
