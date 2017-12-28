package com.sriramr.movieinfo.ui.TvShows.TvShowsDetail.Model;

import com.google.gson.annotations.SerializedName;

public class Networks {

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"Networks{" +
			"name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}