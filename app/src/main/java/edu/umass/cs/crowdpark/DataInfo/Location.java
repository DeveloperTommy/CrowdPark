package edu.umass.cs.crowdpark.dataInfo;

import edu.umass.cs.crowdpark.util.LocationUtil;

/**
 * Created by Tommy on 4/13/2016.
 */
public class Location {
    public double longitude, latitude;

    public Location(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double distance(Location other) {
        return LocationUtil.distance(latitude, other.latitude, longitude, other.longitude, 0, 0);
    }

}
