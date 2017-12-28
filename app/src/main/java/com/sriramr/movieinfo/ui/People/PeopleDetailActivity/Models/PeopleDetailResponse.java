package com.sriramr.movieinfo.ui.People.PeopleDetailActivity.Models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PeopleDetailResponse{

	@SerializedName("birthday")
	private String birthday;

	@SerializedName("also_known_as")
	private List<String> alsoKnownAs;

	@SerializedName("images")
	private Images images;

	@SerializedName("gender")
	private int gender;

	@SerializedName("imdb_id")
	private String imdbId;

	@SerializedName("profile_path")
	private String profilePath;

	@SerializedName("biography")
	private String biography;

	@SerializedName("deathday")
	private Object deathday;

	@SerializedName("place_of_birth")
	private String placeOfBirth;

	@SerializedName("popularity")
	private double popularity;

	@SerializedName("name")
	private String name;

	@SerializedName("combined_credits")
	private CombinedCredits combinedCredits;

	@SerializedName("id")
	private int id;

	@SerializedName("adult")
	private boolean adult;

	public void setBirthday(String birthday){
		this.birthday = birthday;
	}

	public String getBirthday(){
		return birthday;
	}

	public void setAlsoKnownAs(List<String> alsoKnownAs){
		this.alsoKnownAs = alsoKnownAs;
	}

	public List<String> getAlsoKnownAs(){
		return alsoKnownAs;
	}

	public void setImages(Images images){
		this.images = images;
	}

	public Images getImages(){
		return images;
	}

	public void setGender(int gender){
		this.gender = gender;
	}

	public int getGender(){
		return gender;
	}

	public void setImdbId(String imdbId){
		this.imdbId = imdbId;
	}

	public String getImdbId(){
		return imdbId;
	}

	public void setProfilePath(String profilePath){
		this.profilePath = profilePath;
	}

	public String getProfilePath(){
		return profilePath;
	}

	public void setBiography(String biography){
		this.biography = biography;
	}

	public String getBiography(){
		return biography;
	}

	public void setDeathday(Object deathday){
		this.deathday = deathday;
	}

	public Object getDeathday(){
		return deathday;
	}

	public void setPlaceOfBirth(String placeOfBirth){
		this.placeOfBirth = placeOfBirth;
	}

	public String getPlaceOfBirth(){
		return placeOfBirth;
	}

	public void setPopularity(double popularity){
		this.popularity = popularity;
	}

	public double getPopularity(){
		return popularity;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCombinedCredits(CombinedCredits combinedCredits){
		this.combinedCredits = combinedCredits;
	}

	public CombinedCredits getCombinedCredits(){
		return combinedCredits;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setAdult(boolean adult){
		this.adult = adult;
	}

	public boolean isAdult(){
		return adult;
	}


	@Override
 	public String toString(){
		return 
			"PeopleDetailResponse{" + 
			"birthday = '" + birthday + '\'' + 
			",also_known_as = '" + alsoKnownAs + '\'' + 
			",images = '" + images + '\'' + 
			",gender = '" + gender + '\'' + 
			",imdb_id = '" + imdbId + '\'' + 
			",profile_path = '" + profilePath + '\'' + 
			",biography = '" + biography + '\'' + 
			",deathday = '" + deathday + '\'' + 
			",place_of_birth = '" + placeOfBirth + '\'' + 
			",popularity = '" + popularity + '\'' + 
			",name = '" + name + '\'' + 
			",combined_credits = '" + combinedCredits + '\'' + 
			",id = '" + id + '\'' + 
			",adult = '" + adult + '\'' +
			"}";
		}
}