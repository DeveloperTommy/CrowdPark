package edu.umass.cs.crowdpark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.umass.cs.crowdpark.util.LocationUtil;
import twitter4j.Twitter;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventsTabFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventsTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsTabFragment extends Fragment {

    //Adapter Stuff
    public static final String FIRST_COLUMN="First";
    public static final String SECOND_COLUMN="Second";
    public static final String THIRD_COLUMN="Third";

    //Adapter list
    private ArrayList<HashMap<String, String>> list;

    Twitter twitter;
    RequestToken requestToken = null;
    AccessToken accessToken;
    String oauth_url,oauth_verifier,profile_url;
    SharedPreferences pref;

    public FragmentTransaction ft;
    public Fragment fr;
    public FragmentManager fm;

    //Firebase
    Firebase myFirebaseRef;
    final String FIREBASE_TAG = "Firebase";

    double latitude, longitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events_tab, container, false);

        pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        latitude = Double.parseDouble(pref.getString("LATITUDE", ""));
        longitude = Double.parseDouble(pref.getString("LONGITUDE", ""));

        //Add Event button
        Button addEventButton = (Button) view.findViewById(R.id.addEventButton);

        if (addEventButton != null) {
            addEventButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AddEventActivity.class);
                    startActivity(intent);

                }
            });
        }


        //List Adapter
        ListView listView=(ListView) view.findViewById(R.id.eventListView);

        list=new ArrayList<HashMap<String,String>>();

        HashMap<String,String> categories = new HashMap<String, String>();
        categories.put(FIRST_COLUMN, "Event");
        categories.put(SECOND_COLUMN, "Distance (mi)");
        categories.put(THIRD_COLUMN, "Description");
        list.add(categories);

        Firebase.setAndroidContext(getActivity());
        myFirebaseRef = new Firebase("https://burning-fire-7390.firebaseio.com/events");

        Query queryRef = myFirebaseRef.orderByKey();

        Log.v(FIREBASE_TAG, "Firebase");

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                Log.v(FIREBASE_TAG, "Snapshot: " + snapshot.toString());
                Log.v(FIREBASE_TAG, "Name: " + snapshot.child("name").getValue().toString());
                Log.v(FIREBASE_TAG, "Desc: " + snapshot.child("description").getValue().toString());
                Log.v(FIREBASE_TAG, "Lat: " + snapshot.child("location").child("latitude").getValue().toString());
                Log.v(FIREBASE_TAG, "Lon: " + snapshot.child("location").child("longitude").getValue().toString());

                String eventName = snapshot.child("name").getValue().toString();
                String eventDesc = snapshot.child("description").getValue().toString();
                double lat = Double.parseDouble(snapshot.child("location").child("latitude").getValue().toString());
                double lon = Double.parseDouble(snapshot.child("location").child("longitude").getValue().toString());

                double distance = LocationUtil.distance(lat, latitude, lon, longitude, 0, 0);

                DecimalFormat df = new DecimalFormat("#.###");

                HashMap<String,String> temp=new HashMap<String, String>();
                temp.put(FIRST_COLUMN, eventName);
                temp.put(SECOND_COLUMN, df.format(distance));
                temp.put(THIRD_COLUMN, eventDesc);
                list.add(temp);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }


        });

        EventsTabAdapter adapter = new EventsTabAdapter(getActivity(), list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                Log.v("Hello", "clicked");
            }

        });

        return view;
    }

/*
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
*/
        protected void onPostExecute(List<String> response) {

            Log.v("Hello", "Valid Tweets: \n");

            for (String tweet: response) {
                Log.v("Hello", tweet);
            }


        }
    }




