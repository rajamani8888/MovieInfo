package com.sriramr.movieinfo.ui.tvshows.showsdetail.models;

import com.google.gson.annotations.SerializedName;

public class CreatedBy {

    @SerializedName("gender")
    private int gender;

    @SerializedName("name")
    private String name;

    @SerializedName("profile_path")
    private Object profilePath;

    @SerializedName("id")
    private int id;

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getGender() {
        return gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setProfilePath(Object profilePath) {
        this.profilePath = profilePath;
    }

    public Object getProfilePath() {
        return profilePath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return
                "CreatedBy{" +
                        "gender = '" + gender + '\'' +
                        ",name = '" + name + '\'' +
                        ",profile_path = '" + profilePath + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}