package com.sriramr.movieinfo.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ShowsDao {

    @Query("SELECT * FROM shows WHERE isFavourite = 1")
    List<ShowsWrapper> getFavouiteShows();

    @Query("SELECT * FROM shows WHERE isWatched = 1")
    List<ShowsWrapper> getWatchedShows();

    @Query("SELECT * FROM shows WHERE isWatchLater = 1")
    List<ShowsWrapper> getWatchLaterShows();

    @Query("SELECT * FROM shows WHERE id = :id")
    ShowsWrapper getShowWithId(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertShow(ShowsWrapper show);

}
