package edu.umass.cs.crowdpark.DataInfo;

/**
 * Created by Tommy on 4/13/2016.
 */
public class ParkingInfo {
    public Location location;
    public String zipcode;
    public int numSpaces;
    public double cost;
    public String openingTime;
    public String closingTime;
    public ParkingType type;

    public boolean addEvent(EventInfo event) {
        //TODO make an arraylist to holds event info?
        return false;
    }

    public boolean update(ParkingInfo info) {
        //TODO Decide how to update things
        return false;
    }



}
