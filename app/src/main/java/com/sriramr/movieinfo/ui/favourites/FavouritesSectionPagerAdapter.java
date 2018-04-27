package com.sriramr.movieinfo.ui.favourites;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FavouritesSectionPagerAdapter extends FragmentPagerAdapter {

    private String type;

    public FavouritesSectionPagerAdapter(FragmentManager fm, String type) {
        super(fm);
        this.type = type;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return FavouritesMoviesFragment.newInstance(type);
            case 1:
                return FavouriteShowsFragment.newInstance(type);
            default:
                return FavouritesMoviesFragment.newInstance(type);
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
