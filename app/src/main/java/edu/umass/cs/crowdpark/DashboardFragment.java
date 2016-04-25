package edu.umass.cs.crowdpark;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.InputStream;
import java.net.URL;

import edu.umass.cs.crowdpark.util.TweetUtil;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashboardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends android.support.v4.app.Fragment {
    TextView prof_name;
    SharedPreferences pref;
    Bitmap bitmap;
    ImageView prof_img,tweet, signout, post_tweet;
    EditText tweet_text;
    ProgressDialog progress;
    Dialog tDialog;
    String tweetText;

    Firebase myFirebaseRef;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.dashboard_fragment, container, false);
        prof_name = (TextView)view.findViewById(R.id.prof_name);
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        prof_img = (ImageView)view.findViewById(R.id.prof_image);
        tweet = (ImageView)view.findViewById(R.id.tweet);
        signout = (ImageView)view.findViewById(R.id.signout);
        signout.setOnClickListener(new SignOut());
        tweet.setOnClickListener(new Tweet());
        new LoadProfile().execute();

        myFirebaseRef = new Firebase("https://burning-fire-7390.firebaseio.com/");
        myFirebaseRef.child("message").setValue("Database connection and write to profile successful");

        return view;
    }
    private class SignOut implements View.OnClickListener {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub

            SharedPreferences.Editor edit = pref.edit();
            edit.putString("ACCESS_TOKEN", "");
            edit.putString("ACCESS_TOKEN_SECRET", "");
            edit.commit();
            Fragment login = new LoginFragment();
            FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, login);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();


        }

    }
    private class Tweet implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            /*
            tDialog = new Dialog(getActivity());
            tDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            tDialog.setContentView(R.layout.tweet_dialog);
            tweet_text = (EditText)tDialog.findViewById(R.id.tweet_text);
            post_tweet = (ImageView)tDialog.findViewById(R.id.post_tweet);
            post_tweet.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new PostTweet().execute();
                }
            });

            tDialog.show();
            */

            new PostTweet().execute();


        }}

    private class PostTweet extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(getActivity());
            progress.setMessage("Posting tweet ...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);

            Activity currActivity = getActivity();

            String zip = ((EditText) currActivity.findViewById(R.id.zip_text)).getText().toString();
            String spaces = ((EditText) currActivity.findViewById(R.id.spaces_text)).getText().toString();
            String cost = ((EditText) currActivity.findViewById(R.id.cost_text)).getText().toString();
            String open = ((EditText) currActivity.findViewById(R.id.open_text)).getText().toString();
            String close = ((EditText) currActivity.findViewById(R.id.closing_text)).getText().toString();
            String type = "";

            String lat = pref.getString("LATITUDE", "");
            String lon = pref.getString("LONGITUDE", "");


//            double lat = Double.parseDouble(pref.getString("LATITUDE", ""));
//            double lon = Double.parseDouble(pref.getString("LONGITUDE", ""));


            //tweetText = tweet_text.getText().toString();
            tweetText = TweetUtil.createTweet(zip, spaces, cost, open, close, type, lat, lon);
            progress.show();

        }
        protected String doInBackground(String... args) {

            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(pref.getString("CONSUMER_KEY", ""));
            builder.setOAuthConsumerSecret(pref.getString("CONSUMER_SECRET", ""));

            Log.v("Hello", "Key" + pref.getString("CONSUMER_KEY", ""));
            Log.v("Hello", "Secret " + pref.getString("CONSUMER_SECRET", ""));

            AccessToken accessToken = new AccessToken(pref.getString("ACCESS_TOKEN", ""), pref.getString("ACCESS_TOKEN_SECRET", ""));
            Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);

            Log.v("Hello", "" + tweetText.length());

            try {
                twitter4j.Status response = twitter.updateStatus(tweetText);
                Log.v("Hello", response.getText());
                return response.toString();
            } catch (TwitterException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.v("Hello", e.toString());
            }


            return null;
        }
        protected void onPostExecute(String res) {
            if(res != null){
                progress.dismiss();
                Toast.makeText(getActivity(), "Tweet Sucessfully Posted", Toast.LENGTH_SHORT).show();
//                tDialog.dismiss();
            }else{
                progress.dismiss();
                Toast.makeText(getActivity(), "Error while tweeting !", Toast.LENGTH_SHORT).show();
//                tDialog.dismiss();
            }

        }
    }
    private class LoadProfile extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(getActivity());
            progress.setMessage("Loading Profile ...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(pref.getString("IMAGE_URL", "")).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        protected void onPostExecute(Bitmap image) {
            if (bitmap != null) {
                Bitmap image_circle = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

                BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Paint paint = new Paint();
                paint.setShader(shader);
                Canvas c = new Canvas(image_circle);
                c.drawCircle(image.getWidth() / 2, image.getHeight() / 2, image.getWidth() / 2, paint);
                prof_img.setImageBitmap(image_circle);
                prof_name.setText("Welcome " + pref.getString("NAME", ""));
            }

            progress.hide();

        }
    }

}