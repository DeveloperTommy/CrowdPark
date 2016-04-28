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

    }

    @Test
    public void testCreateTweet() throws Exception {
        String tweet = tweetUtil.createTweet("Campus Center Garage", "10", "5", "9AM", "11PM", "garage", "lat", "long");
        String expectedTweet = "#crowdpark ; Lat: lat ; Lon: long ; Name: Campus Center Garage ; Spaces: 10 ; Cost: 5 ; Open: 9AM ; Close: 11PM ; Type: garage";


        assertEquals(tweet, expectedTweet);
    }

    @Test
    public void testTest1() throws Exception {

    }
}