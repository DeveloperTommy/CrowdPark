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
public class ViewAddLocationTest {

    private static final String name = "Campus Center Garage";
    private static final String spaces = "10";
    private static final String cost = "5";
    private static final String open = "9AM";
    private static final String close = "11PM";


    @Test
    public void addLocationTextFieldValid(){
        onView(withId(R.id.name_text)).perform(typeText(name), closeSoftKeyboard()); //line 1
        onView(withId(R.id.spaces_text)).perform(typeText(spaces), closeSoftKeyboard()); //line 1
        onView(withId(R.id.cost_text)).perform(typeText(cost), closeSoftKeyboard()); //line 1
        onView(withId(R.id.open_text)).perform(typeText(open), closeSoftKeyboard()); //line 1
        onView(withId(R.id.closing_text)).perform(typeText(close), closeSoftKeyboard()); //line 1

        onView(withId(R.id.tweet)).perform(click());

        String expectedName = name;
        String expectedSpaces = spaces;
        String expectedCost = cost;
        String expectedOpen = open;
        String expectedClose = close;

        onView(withId(R.id.name_text)).check(matches(withText(expectedName))); //line 3
        onView(withId(R.id.spaces_text)).check(matches(withText(expectedSpaces))); //line 3
        onView(withId(R.id.cost_text)).check(matches(withText(expectedCost))); //line 3
        onView(withId(R.id.open_text)).check(matches(withText(expectedOpen))); //line 3
        onView(withId(R.id.closing_text)).check(matches(withText(expectedClose))); //line 3

    }

}
