package com.sriramr.movieinfo.ui.people.peoplepopular.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class PopularPeople {

    @SerializedName("popularity")
    public double popularity;

    @SerializedName("name")
    public String name;

    @SerializedName("profile_path")
    public String profilePath;

    @SerializedName("id")
    public int id;

    @SerializedName("adult")
    public boolean adult;

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public boolean isAdult() {
        return adult;
    }

    @Override
    public String toString() {
        return
                "PopularPeople{" +
                        "popularity = '" + popularity + '\'' +
                        ",name = '" + name + '\'' +
                        ",profile_path = '" + profilePath + '\'' +
                        ",id = '" + id + '\'' +
                        ",adult = '" + adult + '\'' +
                        "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PopularPeople)) return false;
        PopularPeople people = (PopularPeople) obj;
        return this.id == people.id;
    }
}