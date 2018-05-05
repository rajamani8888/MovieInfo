package com.sriramr.movieinfo.network;

import com.sriramr.movieinfo.ui.discover.discoverdetail.models.DiscoverMoviesResponse;
import com.sriramr.movieinfo.ui.discover.discoverdetail.models.DiscoverShowResponse;
import com.sriramr.movieinfo.ui.movies.moviedetail.models.MovieDetailResponse;
import com.sriramr.movieinfo.ui.movies.movielist.models.MovieListResponse;
import com.sriramr.movieinfo.ui.people.peopledetail.models.PeopleDetailResponse;
import com.sriramr.movieinfo.ui.people.peoplepopular.models.PopularPeopleResponse;
import com.sriramr.movieinfo.ui.tvshows.showsdetail.models.TvShowDetailResponse;
import com.sriramr.movieinfo.ui.tvshows.showslist.models.TvShowsResponse;
import com.sriramr.movieinfo.utils.AppConstants;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class NetworkRepository {

    private static MovieService mService;

    private NetworkRepository() {
    }

    public static void init(MovieService service) {
        mService = service;
    }

    public static Observable<DiscoverMoviesResponse> getDiscoverMovies(String genreId, int page) {
        return mService.getDiscoveredMovies(AppConstants.API_KEY, genreId, page);
    }

    public static Observable<DiscoverShowResponse> getDiscoverShows(String genreId, int page) {
        return mService.getDiscoverShows(AppConstants.API_KEY, genreId, page);
    }

    public static Observable<MovieListResponse> getNowPlayingMovies(int page) {
        return mService.getNowPlayingMovies(page, AppConstants.API_KEY);
    }

    public static Observable<MovieListResponse> getPopularMovies(int page) {
        return mService.getPopularMovies(page, AppConstants.API_KEY);
    }

    public static Observable<MovieListResponse> getTopRatedMovies(int page) {
        return mService.getTopRatedMovies(page, AppConstants.API_KEY);
    }

    public static Observable<MovieListResponse> getUpcomingMovies(int page) {
        return mService.getUpcomingMovies(page, AppConstants.API_KEY);
    }

    public static Observable<MovieDetailResponse> getMovieDetails(String movieId) {
        return mService.getMovieDetails(movieId, AppConstants.API_KEY, AppConstants.MOVIE_APPEND_TO_RESPONSE);
    }

    public static Observable<TvShowsResponse> getAiringTodayShows(int page) {
        return mService.getAiringTodayTvShows(AppConstants.API_KEY, page);
    }

    public static Observable<TvShowsResponse> getOnAirTvShows(int page) {
        return mService.getOnTheAirTvShows(AppConstants.API_KEY, page);
    }

    public static Observable<TvShowsResponse> getPopularShows(int page) {
        return mService.getPopularTv(AppConstants.API_KEY, page);
    }

    public static Observable<TvShowsResponse> getTopRatedShows(int page) {
        return mService.getTopRatedTv(AppConstants.API_KEY, page);
    }

    public static Observable<TvShowDetailResponse> getShowDetails(String showId) {
        return mService.getDetailTvShow(showId, AppConstants.API_KEY, AppConstants.TV_SHOW_APPEND_TO_RESPONSE);
    }

    public static Observable<PopularPeopleResponse> getPopularPeople(String apiKey, int page) {
        return mService.getPopularPeople(apiKey, page);
    }

    public static Observable<PeopleDetailResponse> getPeopleDetail(String id) {
        return mService.getPersonDetail(id,AppConstants.API_KEY, AppConstants.STAR_APPEND_TO_RESPONSE);
    }
}
