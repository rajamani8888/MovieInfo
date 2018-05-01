package com.sriramr.movieinfo.ui.tvshows.showsdetail.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowDetailResponse {

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("number_of_episodes")
    private int numberOfEpisodes;

    @SerializedName("videos")
    private Videos videos;

    @SerializedName("networks")
    private List<Networks> networks;

    @SerializedName("type")
    private String type;

    @SerializedName("recommendations")
    private Recommendations recommendations;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("credits")
    private Credits credits;

    @SerializedName("genres")
    private List<Genres> genres;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("id")
    private int id;

    @SerializedName("number_of_seasons")
    private int numberOfSeasons;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("overview")
    private String overview;

    @SerializedName("seasons")
    private List<Seasons> seasons;

    @SerializedName("images")
    private Images images;

    @SerializedName("languages")
    private List<String> languages;

    @SerializedName("created_by")
    private List<CreatedBy> createdBy;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("origin_country")
    private List<String> originCountry;

    @SerializedName("production_companies")
    private List<ProductionCompanies> productionCompanies;

    @SerializedName("original_name")
    private String originalName;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("name")
    private String name;

    @SerializedName("episode_run_time")
    private List<Integer> episodeRunTime;

    @SerializedName("in_production")
    private boolean inProduction;

    @SerializedName("last_air_date")
    private String lastAirDate;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("status")
    private String status;

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setVideos(Videos videos) {
        this.videos = videos;
    }

    public Videos getVideos() {
        return videos;
    }

    public void setNetworks(List<Networks> networks) {
        this.networks = networks;
    }

    public List<Networks> getNetworks() {
        return networks;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setRecommendations(Recommendations recommendations) {
        this.recommendations = recommendations;
    }

    public Recommendations getRecommendations() {
        return recommendations;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setCredits(Credits credits) {
        this.credits = credits;
    }

    public Credits getCredits() {
        return credits;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOverview() {
        return overview;
    }

    public void setSeasons(List<Seasons> seasons) {
        this.seasons = seasons;
    }

    public List<Seasons> getSeasons() {
        return seasons;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public Images getImages() {
        return images;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setCreatedBy(List<CreatedBy> createdBy) {
        this.createdBy = createdBy;
    }

    public List<CreatedBy> getCreatedBy() {
        return createdBy;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setProductionCompanies(List<ProductionCompanies> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public List<ProductionCompanies> getProductionCompanies() {
        return productionCompanies;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEpisodeRunTime(List<Integer> episodeRunTime) {
        this.episodeRunTime = episodeRunTime;
    }

    public List<Integer> getEpisodeRunTime() {
        return episodeRunTime;
    }

    public void setInProduction(boolean inProduction) {
        this.inProduction = inProduction;
    }

    public boolean isInProduction() {
        return inProduction;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return
                "TvShowDetailResponse{" +
                        "original_language = '" + originalLanguage + '\'' +
                        ",number_of_episodes = '" + numberOfEpisodes + '\'' +
                        ",videos = '" + videos + '\'' +
                        ",networks = '" + networks + '\'' +
                        ",type = '" + type + '\'' +
                        ",recommendations = '" + recommendations + '\'' +
                        ",backdrop_path = '" + backdropPath + '\'' +
                        ",credits = '" + credits + '\'' +
                        ",genres = '" + genres + '\'' +
                        ",popularity = '" + popularity + '\'' +
                        ",id = '" + id + '\'' +
                        ",number_of_seasons = '" + numberOfSeasons + '\'' +
                        ",vote_count = '" + voteCount + '\'' +
                        ",first_air_date = '" + firstAirDate + '\'' +
                        ",overview = '" + overview + '\'' +
                        ",seasons = '" + seasons + '\'' +
                        ",images = '" + images + '\'' +
                        ",languages = '" + languages + '\'' +
                        ",created_by = '" + createdBy + '\'' +
                        ",poster_path = '" + posterPath + '\'' +
                        ",origin_country = '" + originCountry + '\'' +
                        ",production_companies = '" + productionCompanies + '\'' +
                        ",original_name = '" + originalName + '\'' +
                        ",vote_average = '" + voteAverage + '\'' +
                        ",name = '" + name + '\'' +
                        ",episode_run_time = '" + episodeRunTime + '\'' +
                        ",in_production = '" + inProduction + '\'' +
                        ",last_air_date = '" + lastAirDate + '\'' +
                        ",homepage = '" + homepage + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}