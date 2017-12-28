package com.sriramr.movieinfo.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MoviesDao {

    @Query("SELECT * FROM movies WHERE isFavourite = 1")
    List<MovieWrapper> getFavouriteMovies();

    @Query("SELECT * FROM movies WHERE isWatched = 1")
    List<MovieWrapper> getWatchedMovies();

    @Query("SELECT * FROM movies WHERE isWatchLater = 1")
    List<MovieWrapper> getWatchLaterMovies();

    @Query("SELECT * FROM movies WHERE id = :id")
    MovieWrapper getMovieWithId(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MovieWrapper movie);

}
