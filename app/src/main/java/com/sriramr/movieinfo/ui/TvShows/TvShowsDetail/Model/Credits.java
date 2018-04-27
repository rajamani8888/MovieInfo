package com.sriramr.movieinfo.ui.TvShows.TvShowsDetail.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Credits {

    @SerializedName("cast")
    private List<Cast> cast;

    @SerializedName("crew")
    private List<Object> crew;

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCrew(List<Object> crew) {
        this.crew = crew;
    }

    public List<Object> getCrew() {
        return crew;
    }

    @Override
    public String toString() {
        return
                "Credits{" +
                        "cast = '" + cast + '\'' +
                        ",crew = '" + crew + '\'' +
                        "}";
    }
}