package edu.umass.cs.crowdpark;


import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by David on 4/28/2016.
 */


//Debugging
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ViewAddEventTest {

    private static final String name = "Big Chill";
    private static final String description = "Free food and activities!";


    //Checks inputed values in edit text
    @Test
    public void addLocationTextFieldValid(){
        onView(withId(R.id.event_name_text)).perform(typeText(name), closeSoftKeyboard()); //line 1
        onView(withId(R.id.event_description_text)).perform(typeText(description), closeSoftKeyboard()); //line 1

        onView(withId(R.id.submitButton)).perform(click());

        String expectedName = name;
        String expectedSpaces = description;

        onView(withId(R.id.name_text)).check(matches(withText(expectedName))); //line 3
        onView(withId(R.id.spaces_text)).check(matches(withText(expectedSpaces))); //line 3

    }

    @Test
    public void testSubmitButton(){

    }

    @Test
    public void testDiscardButton(){

    }
}
