package com.sriramr.movieinfo.ui.movies.moviedetail.models;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Credits {

    @SerializedName("cast")
    private List<Cast> cast;

    @SerializedName("crew")
    private List<Crew> crew;

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    public List<Crew> getCrew() {
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