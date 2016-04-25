package edu.umass.cs.crowdpark;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;


/**
 * Created by David on 4/24/2016.
 */
 public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    FindParkingTabFragment tab1 = new FindParkingTabFragment();
                    return tab1;
                case 1:
                    DashboardFragment tab2 = new DashboardFragment();
                    return tab2;
                case 2:
                    EventsTabFragment tab3 = new EventsTabFragment();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }

