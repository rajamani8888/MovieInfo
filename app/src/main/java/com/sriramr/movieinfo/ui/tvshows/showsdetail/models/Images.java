package com.sriramr.movieinfo.ui.tvshows.showsdetail.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Images {

    @SerializedName("backdrops")
    private List<Object> backdrops;

    @SerializedName("posters")
    private List<Object> posters;

    public void setBackdrops(List<Object> backdrops) {
        this.backdrops = backdrops;
    }

    public List<Object> getBackdrops() {
        return backdrops;
    }

    public void setPosters(List<Object> posters) {
        this.posters = posters;
    }

    public List<Object> getPosters() {
        return posters;
    }

    @Override
    public String toString() {
        return
                "Images{" +
                        "backdrops = '" + backdrops + '\'' +
                        ",posters = '" + posters + '\'' +
                        "}";
    }
}