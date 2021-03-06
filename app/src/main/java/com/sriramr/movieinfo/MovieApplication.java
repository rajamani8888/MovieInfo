package com.sriramr.movieinfo;

import android.app.Activity;
import android.app.Application;

import com.sriramr.movieinfo.database.DatabaseRepository;
import com.sriramr.movieinfo.network.NetworkRepository;
import com.sriramr.movieinfo.network.NetworkService;

import timber.log.Timber;

public class MovieApplication extends Application {

    public static MovieApplication get(Activity activity) {
        return (MovieApplication) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        // initialising database repository. Needs to be done only once in the whole application.
        DatabaseRepository.init(this);

        // initialising network repo.
        NetworkRepository.init(NetworkService.getService(this));

    }

}
