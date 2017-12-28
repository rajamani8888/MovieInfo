package com.sriramr.movieinfo.ui.TvShows.TvShowsDetail.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Videos{

	@SerializedName("results")
	private List<Object> results;

	public void setResults(List<Object> results){
		this.results = results;
	}

	public List<Object> getResults(){
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