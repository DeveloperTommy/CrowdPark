package edu.umass.cs.crowdpark.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Tommy on 4/28/2016.
 */

public class SortTest {

    @Test
    public void testDistance() throws Exception {
        DistanceComparator dist = new DistanceComparator();

        dist.setCoords(42.395, -72.523);

        String dist1 = "#crowdpark ; Lat: 42.3952 ; Lon: -72.5232 ; Name: 03292 ; Spaces: 2 ; Cost: 32.43 ; Open: 2AM ; Close: 6PM ; Type: Meter";
        String dist2 = "#crowdpark ; Lat: 42.3954 ; Lon: -72.5234 ; Name: 03292 ; Spaces: 2 ; Cost: 32.43 ; Open: 2AM ; Close: 6PM ; Type: Meter";

        assertEquals(dist.compare(dist1, dist2), -1);
    }

    @Test
    public void testDistanceInvalid() throws Exception {
        DistanceComparator dist = new DistanceComparator();

        try {
            dist.compare("", "");
            assertTrue(false);
        }
        catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCost() throws Exception {

        CostComparator comp = new CostComparator();

        String comp1 = "#crowdpark ; Lat: 42.3952 ; Lon: -72.5232 ; Name: 03292 ; Spaces: 2 ; Cost: 32.43 ; Open: 2AM ; Close: 6PM ; Type: Meter";
        String comp2 = "#crowdpark ; Lat: 42.3954 ; Lon: -72.5234 ; Name: 03292 ; Spaces: 2 ; Cost: 35.43 ; Open: 2AM ; Close: 6PM ; Type: Meter";

        assertEquals(comp.compare(comp1, comp2), -1);
    }

    @Test
    public void testCostInvalid() throws Exception {
        CostComparator cost = new CostComparator();

        try {
            cost.compare("", "");
            assertTrue(false);
        }
        catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void testSpace() throws Exception {
        SpaceComparator comp = new SpaceComparator();

        String space1 = "#crowdpark ; Lat: 42.3952 ; Lon: -72.5232 ; Name: 03292 ; Spaces: 2 ; Cost: 32.43 ; Open: 2AM ; Close: 6PM ; Type: Meter";
        String space2 = "#crowdpark ; Lat: 42.3954 ; Lon: -72.5234 ; Name: 03292 ; Spaces: 4 ; Cost: 35.43 ; Open: 2AM ; Close: 6PM ; Type: Meter";

        assertEquals(comp.compare(space1, space2), -1);
    }

    @Test
    public void testSpaceInvalid() throws Exception {
        SpaceComparator space = new SpaceComparator();

        try {
            space.compare("", "");
            assertTrue(false);
        }
        catch (Exception e) {
            assertTrue(true);
        }
    }

}
