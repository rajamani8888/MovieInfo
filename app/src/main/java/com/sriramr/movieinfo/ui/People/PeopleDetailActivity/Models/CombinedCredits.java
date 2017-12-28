package com.sriramr.movieinfo.ui.People.PeopleDetailActivity.Models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CombinedCredits{

	@SerializedName("cast")
	private List<CastItem> cast;

	public void setCast(List<CastItem> cast){
		this.cast = cast;
	}

	public List<CastItem> getCast(){
		return cast;
	}

	@Override
 	public String toString(){
		return 
			"CombinedCredits{" + 
			"cast = '" + cast + '\'' + 
			"}";
		}
}