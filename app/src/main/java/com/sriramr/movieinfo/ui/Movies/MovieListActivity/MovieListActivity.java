package com.sriramr.movieinfo.ui.Movies.MovieListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.sriramr.movieinfo.ui.favourites.FavouritesActivity;
import com.sriramr.movieinfo.ui.Discover.DiscoverActivity.DiscoverActivity;
import com.sriramr.movieinfo.ui.People.PopularPeopleActivity.PopularPeopleActivity;
import com.sriramr.movieinfo.ui.TvShows.TvShows.TvShowsListFragment;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.utils.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.movie_list_activity_frame)
    FrameLayout movieListActivityFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Movie");

        initViews();

        if (savedInstanceState == null){
            FragmentManager m = getSupportFragmentManager();
            m.beginTransaction()
                    .add(R.id.movie_list_activity_frame,new MovieListFragment())
                    .commit();
        }

    }

    private void initViews() {

        drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.movie_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager manager = getSupportFragmentManager();
        switch (id){
            case R.id.nav_movies:

                manager.beginTransaction()
                        .replace(R.id.movie_list_activity_frame,new MovieListFragment())
                        .commit();
                getSupportActionBar().setTitle("Movie");
                break;
            case R.id.nav_tv_s:
                manager.beginTransaction()
                        .replace(R.id.movie_list_activity_frame,new TvShowsListFragment())
                        .commit();
                getSupportActionBar().setTitle("Tv Shows");
                break;
            case R.id.nav_people:
                Intent i = new Intent(MovieListActivity.this,PopularPeopleActivity.class);
                i.putExtra(AppConstants.TAG,AppConstants.POPULAR_PEOPLE);
                startActivity(i);
                break;
            case R.id.nav_discover:
                startActivity(new Intent(MovieListActivity.this, DiscoverActivity.class));
                break;
            case R.id.nav_favourites:
                Intent f = new Intent(this, FavouritesActivity.class);
                f.putExtra(AppConstants.TAG, AppConstants.FAVOURITES);
                startActivity(f);
                break;
            case R.id.nav_watched:
                Intent watched = new Intent(this, FavouritesActivity.class);
                watched.putExtra(AppConstants.TAG, AppConstants.WATCHED);
                startActivity(watched);
                break;
            case R.id.nav_watch_later:
                Intent watch_later = new Intent(this, FavouritesActivity.class);
                watch_later.putExtra(AppConstants.TAG, AppConstants.WATCH_LATER);
                startActivity(watch_later);
                break;
            default:
                //TODO handle later
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
