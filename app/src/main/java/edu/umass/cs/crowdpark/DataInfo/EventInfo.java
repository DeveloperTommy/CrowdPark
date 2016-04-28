package edu.umass.cs.crowdpark.dataInfo;

/**
 * Created by Tommy on 4/13/2016.
 */
public class EventInfo {

    public String name, description;

    private Location location;

    public EventInfo(String name, String description, double lat, double lon) {
        this.name = name;
        this.description = description;

        location = new Location(lat, lon);
    }

    public Location getLocation() {
        return location;
    }


}
