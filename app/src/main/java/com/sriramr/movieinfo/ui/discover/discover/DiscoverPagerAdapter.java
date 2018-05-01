package com.sriramr.movieinfo.ui.discover.discover;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class DiscoverPagerAdapter extends FragmentPagerAdapter {

    public DiscoverPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        switch (position) {
            case 0:
                return DiscoverMoviesFragment.newInstance();
            case 1:
                return DiscoverShowsFragment.newInstance();
            default:
                // we wont reach here, but if we do, better to show something
                return DiscoverMoviesFragment.newInstance();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Movies";
            case 1:
                return "Tv Shows";
            default:
                return "Movies";
        }
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}
