package edu.umass.cs.crowdpark;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;



public class AddEventActivity extends AppCompatActivity {


   // public FragmentTransaction ft;
   // public Fragment fr;
   // public FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_event);

        //Submit button
        Button submitButton = (Button) findViewById(R.id.submitButton);

        if (submitButton != null) {
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
