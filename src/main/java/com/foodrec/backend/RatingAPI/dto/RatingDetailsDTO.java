package com.foodrec.backend.RatingAPI.dto;

public class RatingDetailsDTO {
    private int raters;
    private double average;

    public int getRaters() {
        return raters;
    }

    public void setRaters(int raters) {
        this.raters = raters;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }
}
