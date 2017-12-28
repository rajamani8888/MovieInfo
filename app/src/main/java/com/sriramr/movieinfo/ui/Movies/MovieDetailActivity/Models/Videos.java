package com.sriramr.movieinfo.ui.Movies.MovieDetailActivity.Models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Videos{

	@SerializedName("results")
	private List<Video> results;

	public void setResults(List<Video> results){
		this.results = results;
	}

	public List<Video> getResults(){
		return results;
	}

	@Override
 	public String toString(){
		return 
			"Videos{" + 
			"results = '" + results + '\'' + 
			"}";
		}
}