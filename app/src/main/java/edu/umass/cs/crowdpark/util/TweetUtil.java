package edu.umass.cs.crowdpark.util;

import android.util.Log;

/**
 * Created by Tommy on 4/24/2016.
 */
public class TweetUtil {

    String twitter = "#crowdpark ; Latitude: 12.12 ; Longitude: 12.4 ; Zip: 03292 ; Spaces: 2 ; Cost: 32.43 ; Opening: 2AM ; Closing: 6PM";

    public static final int LAT = 0, LON = 1, NAME = 2, SPACE = 3, COST = 4, OPEN = 5, CLOSE = 6, TYPE = 7;

    public static String [] parseTweet(String tweet){
        String [] parkingData;
        parkingData = tweet.split(";");
        String [] newData = new String[parkingData.length - 1];
        String [] dataNames = {"#crowdpark", "Lat", "Lon", "Name", "Spaces", "Cost", "Open", "Close", "Type"};

        for(int i = 0; i< parkingData.length; i++){
            String [] currentSplit = parkingData[i].split(":");

            String thing = currentSplit[0].replaceAll("\\s+", "");

            if(thing.equals(dataNames[i])){
                if(i != 0){
                    if(currentSplit.length == 2){
                        newData[i-1] = currentSplit[1];
                    }
                    else{
                        Log.e("Hello","Tweet does not have correct parking information! Size is not equal to 2");
                        return null;
                    }
                }
            }
            else{
                Log.e("Hello", "Tweet does not have correct parking information! String incorrect: " + thing);
                return null;
            }

        }
        return newData;
    }

    public static String createTweet(String name, String spaces, String cost, String open, String close, String type, String lat, String lon) {
        return "#crowdpark ; Lat: " + lat + " ; Lon: " + lon + " ; Name: " + name
                + " ; Spaces: " + spaces + " ; Cost: " + cost + " ; Open: " + open + " ; Close: " + close + " ; Type: " + type;
    }

    public static void test() {
        String twitter = "#crowdpark - Lat: 12.12 - Lon: 12.4 - Zip: 03292 - Spaces: 2 - Cost: 32.42 - Open: 2AM - Close: 6PM";
        String[] result = parseTweet(twitter);

        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }

//        System.out.println(createTweet("03292", "2", "32.42", "2AM", "6PM", "", 12.12, 12.4));

//        System.out.println(createTweet("03292", "2", "32.42", "2AM", "6PM", "", 12.12, 12.4).equals(twitter));
    }


}
