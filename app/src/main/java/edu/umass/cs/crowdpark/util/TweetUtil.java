package edu.umass.cs.crowdpark.util;

import android.util.Log;

/**
 * Created by Tommy on 4/24/2016.
 */
public class TweetUtil {

    public static String [] parseTweet(String tweet){
        String [] parkingData;
        parkingData = tweet.split("-");
        String [] newData = new String[parkingData.length - 1];
        String [] dataNames = {"#crowdpark", "Latitude", "Longitude", "Zipcode", "Spaces", "Cost", "Opening_Time", "Closing_Time"};

        for(int i = 0; i< parkingData.length; i++){
            String [] currentSplit = parkingData[i].split(":");

            String thing = currentSplit[0].replaceAll("\\s+", "");

            if(thing.equals(dataNames[i])){
                if(i != 0){
                    if(currentSplit.length == 2){
                        newData[i-1] = currentSplit[1];
                    }
                    else{
                        Log.e("Hello","Tweet does not have correct parking information!");
                        return null;
                    }
                }
            }
            else{
                Log.e("Hello", "Tweet does not have correct parking information!");
                return null;
            }

        }
        return newData;
    }

}
