package com.example.android.quakereport;

/**
 * EarthQuake Class
 */
public class EarthQuake {
    private double magnitude ;
    private int  dateInMIlliseconds;
    private String place;

    /**
     *
     * @param magnitude
     * @param dateInMIlliseconds
     * @param place
     */
    public EarthQuake(double magnitude, int dateInMIlliseconds, String place) {
        this.magnitude = magnitude;
        this.dateInMIlliseconds = dateInMIlliseconds;
        this.place = place;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public int getDateInMIlliseconds() {
        return dateInMIlliseconds;
    }

    public void setDateInMIlliseconds(int dateInMIlliseconds) {
        this.dateInMIlliseconds = dateInMIlliseconds;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
