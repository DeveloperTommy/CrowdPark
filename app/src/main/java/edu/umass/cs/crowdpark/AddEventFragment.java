package edu.umass.cs.crowdpark;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddEventFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEventFragment extends Fragment {


    public FragmentTransaction ft;
    public Fragment fr;
    public FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);

        //Submit button
        Button submitButton = (Button) view.findViewById(R.id.submitButton);

        if (submitButton != null) {
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    fr = new EventsTabFragment();

                    fm = getFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.addEventFragment, fr);
                    ft.commit();

                   /* EventsTabFragment eventsTab = (EventsTabFragment) getFragmentManager().findFragmentById(R.id.eventsTabFragment);
                    if (eventsTab == null) {
                        // Make new fragment to show this selection.
                        eventsTab = AddEventFragment.newInstance(1);

                        // Execute a transaction, replacing any existing
                        // fragment with this one inside the frame.
                        ft
                                = getFragmentManager().beginTransaction();
                        ft.add(R.id.eventsTabFragment, eventsTab, "events tab");
                        ft.setTransition(
                                FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.commit();
                    }*/

                }
            });
        }


        return view;
    }



}
