package com.sriramr.movieinfo.database;

import android.app.Application;
import android.arch.persistence.room.Room;

import java.util.List;

public class DatabaseRepository {


    private static AppDatabase database;

    public static void init(Application context){
        if (database == null){
            database = Room.databaseBuilder(context, AppDatabase.class,"movie-db-info").allowMainThreadQueries().build();
        }
    }

    public static MovieWrapper getMovieWithId( String id){
        return database.getMoviesDao().getMovieWithId(id);
    }

    public static List<MovieWrapper> getFavouriteMovies(){
        return database.getMoviesDao().getFavouriteMovies();
    }

    public static List<MovieWrapper> getWatchedMovies(){
        return database.getMoviesDao().getWatchedMovies();
    }

    public static List<MovieWrapper> getWatchLaterMovies(){
        return database.getMoviesDao().getWatchLaterMovies();
    }

    public static void insertMovies(MovieWrapper movie){
        database.getMoviesDao().insertMovie(movie);
    }



    public static ShowsWrapper getShowWithId(String id){
        return database.getShowsDao().getShowWithId(id);
    }

    public static List<ShowsWrapper> getFavouriteShows(){
        return database.getShowsDao().getFavouiteShows();
    }

    public static List<ShowsWrapper> getWatchedShows(){
        return database.getShowsDao().getWatchedShows();
    }

    public static List<ShowsWrapper> getWatchLaterShows(){
        return database.getShowsDao().getWatchLaterShows();
    }

    public static void insertShows(ShowsWrapper show){
        database.getShowsDao().insertShow(show);
    }

}
