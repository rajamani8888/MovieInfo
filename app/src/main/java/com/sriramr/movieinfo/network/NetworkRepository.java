package com.sriramr.movieinfo.network;

import com.sriramr.movieinfo.ui.discover.discoverdetail.models.DiscoverMoviesResponse;
import com.sriramr.movieinfo.ui.discover.discoverdetail.models.DiscoverShowResponse;
import com.sriramr.movieinfo.utils.AppConstants;

import io.reactivex.Observable;

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

}