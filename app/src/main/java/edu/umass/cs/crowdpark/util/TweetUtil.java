package edu.umass.cs.crowdpark.util;

/**
 * Created by Tommy on 4/24/2016.
 */
public class TweetUtil {

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
                        newData[i-1] = currentSplit[1].replaceAll("\\s+", "");
                    }
                    else{
//                        Log.e("Hello","Tweet does not have correct parking information! Size is not equal to 2");
                        return null;
                    }
                }
            }
            else{
//                Log.e("Hello", "Tweet does not have correct parking information! String incorrect: " + thing);
                return null;
            }

        }
        return newData;
    }

    public static String createTweet(String name, String spaces, String cost, String open, String close, String type, String lat, String lon) {
        return "#crowdpark ; Lat: " + lat + " ; Lon: " + lon + " ; Name: " + name
                + " ; Spaces: " + spaces + " ; Cost: " + cost + " ; Open: " + open + " ; Close: " + close + " ; Type: " + type;
    }


}
