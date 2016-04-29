package edu.umass.cs.crowdpark.util;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Created by David on 4/28/2016.
 */
public class TweetUtilTest {

    private TweetUtil tweetUtil;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testParseTweet() throws Exception {
        String twitter = "#crowdpark ; Lat: 12.12 ; Lon: 12.4 ; Name: 03292 ; Spaces: 2 ; Cost: 32.43 ; Open: 2AM ; Close: 6PM ; Type: Meter";
        String[] parsed = tweetUtil.parseTweet(twitter);

        assertEquals(parsed[0], "12.12");
        assertEquals(parsed[1], "12.4");
        assertEquals(parsed[2], "03292");
        assertEquals(parsed[3], "2");
        assertEquals(parsed[4], "32.43");
        assertEquals(parsed[5], "2AM");
        assertEquals(parsed[6], "6PM");
        assertEquals(parsed[7], "Meter");

    }

    @Test
    public void testParseInvalidTweet() throws Exception {
        String twitter = "#crowdpark ; Lat: 12.12 ; Lon: 12.4 ; Name: 03292 ; Spaces: 2 ; Cost: 32.43 ; Open: 2AM ; Close: 6PM ; Type:";
        String[] parsed = tweetUtil.parseTweet(twitter);

        assertEquals(parsed, null);
    }

    @Test
    public void testCreateTweet() throws Exception {
        String tweet = tweetUtil.createTweet("Campus Center Garage", "10", "5", "9AM", "11PM", "garage", "lat", "long");
        String expectedTweet = "#crowdpark ; Lat: lat ; Lon: long ; Name: Campus Center Garage ; Spaces: 10 ; Cost: 5 ; Open: 9AM ; Close: 11PM ; Type: garage";


        assertEquals(tweet, expectedTweet);
    }

}