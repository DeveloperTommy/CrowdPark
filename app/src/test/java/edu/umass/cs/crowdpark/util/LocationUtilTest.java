package edu.umass.cs.crowdpark.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Tommy on 4/28/2016.
 */
public class LocationUtilTest {

    private LocationUtil locUtil;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testDistanceStationary() throws Exception {

        double lat1 = 42.395;
        double lat2 = 42.395;
        double lon1 = -72.523;
        double lon2 = -72.523;

        double distance = locUtil.distance(lat1, lat2, lon1, lon2, 0, 0);

        assertEquals(distance, 0.0, 2);
    }

    @Test
    public void testSmallDistance() throws Exception {

        double lat1 = 42.39536;
        double lat2 = 42.39531;
        double lon1 = -72.52528;
        double lon2 = -72.52522;

        double distance = locUtil.distance(lat1, lat2, lon1, lon2, 0, 0);

        assertEquals(distance, 8, 2);
    }


}