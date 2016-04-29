package edu.umass.cs.crowdpark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import edu.umass.cs.crowdpark.util.CostComparator;
import edu.umass.cs.crowdpark.util.DistanceComparator;
import edu.umass.cs.crowdpark.util.LocationUtil;
import edu.umass.cs.crowdpark.util.SpaceComparator;
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

    //Adapter Stuff
    public static final String FIRST_COLUMN="First";
    public static final String SECOND_COLUMN="Second";
    public static final String THIRD_COLUMN="Third";
    public static final String FOURTH_COLUMN="Fourth";
    public static final String FIFTH_COLUMN="Fifth";
    public static final String SIXTH_COLUMN="Sixth";
    public static final String SEVENTH_COLUMN="Seventh";

    //Adapter list
    private ArrayList<HashMap<String, String>> list;
    ArrayList<String> valid;

    Twitter twitter;
    RequestToken requestToken = null;
    AccessToken accessToken;
    String oauth_url,oauth_verifier,profile_url;
    SharedPreferences pref;

    final String TWEET_TAG = "Twitter";

    SwipeRefreshLayout refresh;

    double latitude, longitude;

    static Comparator<String> comparator;
    ListView listView;

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

        //List Adapter
        listView=(ListView) view.findViewById(R.id.listView);

        list = new ArrayList<HashMap<String,String>>();

        HashMap<String,String> categories = new HashMap<String, String>();
        categories.put(FIRST_COLUMN, "Distance (Meters)");
        categories.put(SECOND_COLUMN, "Name");
        categories.put(THIRD_COLUMN, "Cost");
        categories.put(FOURTH_COLUMN, "Spaces");
        categories.put(FIFTH_COLUMN, "Operating Times");
        list.add(categories);

        ParkingLocationAdapter adapter = new ParkingLocationAdapter(getActivity(), list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Log.v("Hello", "clicked");
                Log.v("Hello", "" + ((TextView) view.findViewById(R.id.lat)).getText());
                String uri = "geo:" + ((TextView) view.findViewById(R.id.lat)).getText() + "," + ((TextView) view.findViewById(R.id.lon)).getText()
                        + "?q=" + ((TextView) view.findViewById(R.id.lat)).getText() + "," + ((TextView) view.findViewById(R.id.lon)).getText() + "(Parking Location)";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                getActivity().startActivity(intent);
            }

        });

        comparator = new DistanceComparator();

        new GetParkingTask().execute();

        //Button instantiation for sorting

        Button distanceButton = (Button) view.findViewById(R.id.dist_button);
        Button costButton = (Button) view.findViewById(R.id.cost_button);
        Button spaceButton = (Button) view.findViewById(R.id.spaces_button);

        distanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Please swipe to another tab then back to refresh", Toast.LENGTH_SHORT).show();

                list = new ArrayList<HashMap<String,String>>();

                HashMap<String,String> categories = new HashMap<String, String>();
                categories.put(FIRST_COLUMN, "Distance (Meters)");
                categories.put(SECOND_COLUMN, "Name");
                categories.put(THIRD_COLUMN, "Cost");
                categories.put(FOURTH_COLUMN, "Spaces");
                categories.put(FIFTH_COLUMN, "Operating Times");
                list.add(categories);

                DistanceComparator dist = new DistanceComparator();
                dist.setCoords(latitude, longitude);
                comparator = dist;

                new GetParkingTask().execute();

                ParkingLocationAdapter adapter = new ParkingLocationAdapter(getActivity(), list);
                listView.setAdapter(adapter);
            }
        });

        costButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Please swipe to another tab then back to refresh", Toast.LENGTH_SHORT).show();

                list = new ArrayList<HashMap<String,String>>();

                HashMap<String,String> categories = new HashMap<String, String>();
                categories.put(FIRST_COLUMN, "Distance (Meters)");
                categories.put(SECOND_COLUMN, "Name");
                categories.put(THIRD_COLUMN, "Cost");
                categories.put(FOURTH_COLUMN, "Spaces");
                categories.put(FIFTH_COLUMN, "Operating Times");
                list.add(categories);

                comparator = new CostComparator();
                new GetParkingTask().execute();

                ParkingLocationAdapter adapter = new ParkingLocationAdapter(getActivity(), list);
                listView.setAdapter(adapter);
            }
        });

        spaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Please swipe to another tab then back to refresh", Toast.LENGTH_SHORT).show();

                list = new ArrayList<HashMap<String,String>>();

                HashMap<String,String> categories = new HashMap<String, String>();
                categories.put(FIRST_COLUMN, "Distance (Meters)");
                categories.put(SECOND_COLUMN, "Name");
                categories.put(THIRD_COLUMN, "Cost");
                categories.put(FOURTH_COLUMN, "Spaces");
                categories.put(FIFTH_COLUMN, "Operating Times");
                list.add(categories);

                comparator = new SpaceComparator();
                new GetParkingTask().execute();

                ParkingLocationAdapter adapter = new ParkingLocationAdapter(getActivity(), list);
                listView.setAdapter(adapter);
            }
        });

        Toast.makeText(getActivity(), "Please swipe to another tab then back to refresh", Toast.LENGTH_SHORT).show();

        return view;
    }


    private class GetParkingTask extends AsyncTask<String, String, List<String>> {
        protected List<String> doInBackground(String... args) {

            valid = new ArrayList<String>();

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
                    Log.v(TWEET_TAG, status.getText() + " Date: " + status.getCreatedAt());

                    String[] curr = TweetUtil.parseTweet(status.getText());

                    if (curr != null) {
                        valid.add(status.getText());
                        Log.v(TWEET_TAG, "Valid Tweet added");
                    }

                }
                //End of querying
            }
            catch (TwitterException e) {
                Log.e(TWEET_TAG, e.toString());
            }

            Log.v(TWEET_TAG, "Size of valid: " + valid.size());

            //Merge sort by distance
            Collections.sort(valid, comparator);

            return valid;
        }

        protected void onPostExecute(List<String> response) {

            Log.v(TWEET_TAG, "Valid Tweets: \n");

            for (String tweet: response) {
                Log.v(TWEET_TAG, tweet);

                String[] result = TweetUtil.parseTweet(tweet);

                double lat = Double.parseDouble(result[TweetUtil.LAT]);
                double lon = Double.parseDouble(result[TweetUtil.LON]);

                double distance = LocationUtil.distance(lat, latitude, lon, longitude, 0, 0);

                DecimalFormat df = new DecimalFormat("#.###");

                HashMap<String,String> temp = new HashMap<String, String>();
                temp.put(FIRST_COLUMN, df.format(distance) + "m");
                temp.put(SECOND_COLUMN, result[TweetUtil.NAME]);
                temp.put(THIRD_COLUMN, "$" + result[TweetUtil.COST]);
                temp.put(FOURTH_COLUMN, result[TweetUtil.SPACE]);
                temp.put(FIFTH_COLUMN, result[TweetUtil.OPEN] + " to " + result[TweetUtil.CLOSE]);
                temp.put(SIXTH_COLUMN, "" + lat);
                temp.put(SEVENTH_COLUMN, "" + lon);
                list.add(temp);

            }

            Log.v(TWEET_TAG, "End of valid Tweets: \n");


        }
    }

}
