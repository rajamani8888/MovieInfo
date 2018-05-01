package com.sriramr.movieinfo.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {MovieWrapper.class, ShowsWrapper.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MoviesDao getMoviesDao();

    public abstract ShowsDao getShowsDao();

}
