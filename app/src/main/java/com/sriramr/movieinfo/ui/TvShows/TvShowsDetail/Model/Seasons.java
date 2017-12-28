package com.sriramr.movieinfo.ui.TvShows.TvShowsDetail.Model;

import com.google.gson.annotations.SerializedName;

public class Seasons {

	@SerializedName("air_date")
	private String airDate;

	@SerializedName("episode_count")
	private int episodeCount;

	@SerializedName("season_number")
	private int seasonNumber;

	@SerializedName("id")
	private int id;

	@SerializedName("poster_path")
	private String posterPath;

	public void setAirDate(String airDate){
		this.airDate = airDate;
	}

	public String getAirDate(){
		return airDate;
	}

	public void setEpisodeCount(int episodeCount){
		this.episodeCount = episodeCount;
	}

	public int getEpisodeCount(){
		return episodeCount;
	}

	public void setSeasonNumber(int seasonNumber){
		this.seasonNumber = seasonNumber;
	}

	public int getSeasonNumber(){
		return seasonNumber;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setPosterPath(String posterPath){
		this.posterPath = posterPath;
	}

	public String getPosterPath(){
		return posterPath;
	}

	@Override
 	public String toString(){
		return 
			"Seasons{" +
			"air_date = '" + airDate + '\'' + 
			",episode_count = '" + episodeCount + '\'' + 
			",season_number = '" + seasonNumber + '\'' + 
			",id = '" + id + '\'' + 
			",poster_path = '" + posterPath + '\'' + 
			"}";
		}
}