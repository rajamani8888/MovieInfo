package com.sriramr.movieinfo.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "shows")
public class ShowsWrapper {

    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String poster;
    private String genre;
    private String voteAverage;
    private int isFavourite;
    private int isWatched;
    private int isWatchLater;

    public ShowsWrapper(@NonNull String id, String title, String poster, String genre, String voteAverage) {
        this.title = title;
        this.id = id;
        this.poster = poster;
        this.genre = genre;
        this.voteAverage = voteAverage;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(int isFavourite) {
        this.isFavourite = isFavourite;
    }

    public int getIsWatched() {
        return isWatched;
    }

    public void setIsWatched(int isWatched) {
        this.isWatched = isWatched;
    }

    public int getIsWatchLater() {
        return isWatchLater;
    }

    public void setIsWatchLater(int isWatchLater) {
        this.isWatchLater = isWatchLater;
    }

    public void setDefaults() {
        isFavourite = 0;
        isWatched = 0;
        isWatchLater = 0;
    }
}
