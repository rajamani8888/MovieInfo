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
        return FavouritesMoviesFragment.newInstance(type);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
