package com.sriramr.movieinfo.ui.people.peopledetail.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Images {

    @SerializedName("profiles")
    private List<ProfilesItem> profiles;

    public void setProfiles(List<ProfilesItem> profiles) {
        this.profiles = profiles;
    }

    public List<ProfilesItem> getProfiles() {
        return profiles;
    }

    @Override
    public String toString() {
        return
                "Images{" +
                        "profiles = '" + profiles + '\'' +
                        "}";
    }
}