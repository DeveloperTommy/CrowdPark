package edu.umass.cs.crowdpark.util;

import java.util.Comparator;

/**
 * Created by Tommy on 4/26/2016.
 */
public class DistanceComparator implements Comparator<String> {

    double latitude, longitude;

    @Override
    public int compare(String lhs, String rhs) {
        String[] left = TweetUtil.parseTweet(lhs);
        String[] right = TweetUtil.parseTweet(rhs);

        double leftLat = Double.parseDouble(left[TweetUtil.LAT]);
        double leftLon = Double.parseDouble(left[TweetUtil.LON]);

        double rightLat = Double.parseDouble(right[TweetUtil.LAT]);
        double rightLon = Double.parseDouble(right[TweetUtil.LON]);

        //Compare by DIST
        Double leftDistance = LocationUtil.distance(leftLat, latitude, leftLon, longitude, 0, 0);
        Double rightDistance = LocationUtil.distance(rightLat, latitude, rightLon, longitude, 0, 0);

        return leftDistance.compareTo(rightDistance);

    }

    public void setCoords(double lat, double lon) {
        latitude = lat;
        longitude = lon;
    }

}
