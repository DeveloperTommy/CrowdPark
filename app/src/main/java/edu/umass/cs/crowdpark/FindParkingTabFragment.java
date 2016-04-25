package edu.umass.cs.crowdpark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import edu.umass.cs.crowdpark.util.TweetUtil;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by David on 4/24/2016.
 */
public class FindParkingTabFragment extends Fragment {

    Twitter twitter;
    RequestToken requestToken = null;
    AccessToken accessToken;
    String oauth_url,oauth_verifier,profile_url;
    SharedPreferences pref;

    double latitude, longitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_find_parking_tab, container, false);

        pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        latitude = Double.parseDouble(pref.getString("LATITUDE", ""));
        longitude = Double.parseDouble(pref.getString("LONGITUDE", ""));

        Button mapsButton = (Button) view.findViewById(R.id.locationButton);

        if (mapsButton != null) {
            mapsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uri = "geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(Parking Location)";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    getActivity().startActivity(intent);
                }
            });
        }


        new GetParkingTask().execute();

        return view;
    }


    private class GetParkingTask extends AsyncTask<String, String, List<String>> {
        protected List<String> doInBackground(String... args) {

            ArrayList<String> valid = new ArrayList<String>();

            try {

                ConfigurationBuilder builder = new ConfigurationBuilder();
                builder.setOAuthConsumerKey(pref.getString("CONSUMER_KEY", ""));
                builder.setOAuthConsumerSecret(pref.getString("CONSUMER_SECRET", ""));

                accessToken = new AccessToken(pref.getString("ACCESS_TOKEN", ""), pref.getString("ACCESS_TOKEN_SECRET", ""));
                Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);

                //Testing if querying works
                QueryResult result = twitter.search(new twitter4j.Query("#crowdpark"));
                List<twitter4j.Status> tweets = result.getTweets();

                for (twitter4j.Status status : tweets) {
                    Log.v("Hello", status.getText() + " Date: " + status.getCreatedAt());

                    String[] curr = TweetUtil.parseTweet(status.getText());

                    if (curr != null) {
                        valid.add(status.getText());
                    }

                }
                //End of querying
            }
            catch (TwitterException e) {
                Log.e("Hello", e.toString());
            }

            return valid;
        }

        protected void onPostExecute(List<String> response) {

            Log.v("Hello", "Valid Tweets: \n");

            for (String tweet: response) {
                Log.v("Hello", tweet);
            }


        }
    }

}
