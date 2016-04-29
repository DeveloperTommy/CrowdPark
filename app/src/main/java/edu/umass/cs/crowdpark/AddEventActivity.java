package edu.umass.cs.crowdpark;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

import edu.umass.cs.crowdpark.dataInfo.EventInfo;


public class AddEventActivity extends AppCompatActivity {

    SharedPreferences pref;
    Firebase myFirebaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        //Get Preferences
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //Firebase stuff
        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://burning-fire-7390.firebaseio.com/");


        //Submit button
        Button submitButton = (Button) findViewById(R.id.submitButton);

        if (submitButton != null) {
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //GPS
                    double latitude = Double.parseDouble(pref.getString("LATITUDE", ""));
                    double longitude = Double.parseDouble(pref.getString("LONGITUDE", ""));

                    EditText eventText = (EditText) findViewById(R.id.event_name_text);
                    EditText descText = (EditText) findViewById(R.id.event_description_text);

                    String eventName = eventText.getText().toString();
                    String desc = descText.getText().toString();

                    EventInfo newEvent = new EventInfo(eventName, desc, latitude, longitude);

                    //Database
                    myFirebaseRef.child("events").child(eventName).setValue(newEvent);

                    //Go back to previous activity
                    finish();

                }
            });
        }

        //Discard button
        Button discardButton = (Button) findViewById(R.id.discardButton);

        if (discardButton != null) {
            discardButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();

                }
            });
        }


    }



}
