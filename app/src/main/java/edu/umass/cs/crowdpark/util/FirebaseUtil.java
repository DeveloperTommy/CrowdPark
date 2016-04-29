package edu.umass.cs.crowdpark.util;

import android.app.Activity;

import com.firebase.client.Firebase;

//Utility class to maintain one connection to Firebase. DEPRECATED due to poor testability and poor decoupling from other classes.
public class FirebaseUtil {

    private static Firebase self;
    private static Activity context;

    private FirebaseUtil(Activity activity) {
        Firebase.setAndroidContext(activity);
        self = new Firebase("https://burning-fire-7390.firebaseio.com/");
        context = activity;
    }

    public static Firebase getInstance(Activity activity) {
        if (self == null || context != activity) {
            new FirebaseUtil(activity);
            context = activity;
        }

        return self;
    }

}
