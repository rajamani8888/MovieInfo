package com.sriramr.movieinfo.ui.people.peopledetail.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CombinedCredits {

    @SerializedName("cast")
    private List<CastItem> cast;

    public void setCast(List<CastItem> cast) {
        this.cast = cast;
    }

    public List<CastItem> getCast() {
        return cast;
    }

    @Override
    public String toString() {
        return
                "CombinedCredits{" +
                        "cast = '" + cast + '\'' +
                        "}";
    }
}