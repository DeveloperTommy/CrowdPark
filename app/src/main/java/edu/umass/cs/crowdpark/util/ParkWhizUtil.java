package edu.umass.cs.crowdpark.util;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ParkWhizUtil {

    public static String getInfo(String urlName) {
        String result = "";

        try {
            URL url = new URL(urlName);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                result = readStream(in);
            }
            finally {
                urlConnection.disconnect();
            }

        }
        catch (Exception e) {

        }

        return result;
    }

    public static String readStream(InputStream in) {
        //TODO Complete stream reading.
        Log.v("ParkWhiz", in.toString());
        return in.toString();
    }

}
