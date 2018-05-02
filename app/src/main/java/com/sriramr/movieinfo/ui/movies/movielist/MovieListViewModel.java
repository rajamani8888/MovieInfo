package com.sriramr.movieinfo.ui.movies.movielist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sriramr.movieinfo.models.NetworkItems;
import com.sriramr.movieinfo.network.NetworkRepository;
import com.sriramr.movieinfo.ui.movies.movielist.models.Movie;
import com.sriramr.movieinfo.utils.Status;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MovieListViewModel extends AndroidViewModel {

    private MutableLiveData<NetworkItems<Movie>> nowPlayingMovies, topRatedMovies, popularMovies, upcomingMovies;
    private int page = 1;
    private CompositeDisposable compositeDisposable;

    public MovieListViewModel(@NonNull Application application) {
        super(application);
        nowPlayingMovies = new MutableLiveData<>();
        topRatedMovies = new MutableLiveData<>();
        popularMovies = new MutableLiveData<>();
        upcomingMovies = new MutableLiveData<>();
        compositeDisposable = new CompositeDisposable();
    }

    public void refreshNowPlayingMovies() {
        compositeDisposable.add(
                NetworkRepository.getNowPlayingMovies(page).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(nowPlayingResults -> {
                            NetworkItems<Movie> movies = new NetworkItems<>(Status.SUCCESS, nowPlayingResults.getMovies());
                            nowPlayingMovies.setValue(movies);
                        }, throwable -> {
                            NetworkItems<Movie> movies = new NetworkItems<>(Status.FAILURE);
                            nowPlayingMovies.setValue(movies);
                        })
        );
    }

    public void refreshPopularMovies() {
        compositeDisposable.add(
                NetworkRepository.getPopularMovies(page).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(nowPlayingResults -> {
                            NetworkItems<Movie> movies = new NetworkItems<>(Status.SUCCESS, nowPlayingResults.getMovies());
                            popularMovies.setValue(movies);
                        }, throwable -> {
                            NetworkItems<Movie> movies = new NetworkItems<>(Status.FAILURE);
                            popularMovies.setValue(movies);
                        })
        );
    }

    public void refreshTopRatedMovies() {
        compositeDisposable.add(
                NetworkRepository.getTopRatedMovies(page).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(nowPlayingResults -> {
                            NetworkItems<Movie> movies = new NetworkItems<>(Status.SUCCESS, nowPlayingResults.getMovies());
                            topRatedMovies.setValue(movies);
                        }, throwable -> {
                            NetworkItems<Movie> movies = new NetworkItems<>(Status.FAILURE);
                            topRatedMovies.setValue(movies);
                        })
        );
    }

    public void refreshUpcomingMovies() {
        compositeDisposable.add(
                NetworkRepository.getUpcomingMovies(page).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(upcomingMoviesResponse -> {
                            NetworkItems<Movie> movies = new NetworkItems<>(Status.SUCCESS, upcomingMoviesResponse.getMovies());
                            upcomingMovies.setValue(movies);
                        }, throwable -> {
                            NetworkItems<Movie> movies = new NetworkItems<>(Status.FAILURE);
                            upcomingMovies.setValue(movies);
                        })
        );
    }

    public MutableLiveData<NetworkItems<Movie>> getNowPlayingMovies() {

        if (nowPlayingMovies.getValue() == null) {
            refreshNowPlayingMovies();
        }

        return nowPlayingMovies;
    }

    public MutableLiveData<NetworkItems<Movie>> getPopularMovies() {

        if (popularMovies.getValue() == null) {
            refreshPopularMovies();
        }

        return popularMovies;
    }

    public MutableLiveData<NetworkItems<Movie>> getTopRatedMovies() {

        if (topRatedMovies.getValue() == null) {
            refreshTopRatedMovies();
        }

        return topRatedMovies;
    }

    public MutableLiveData<NetworkItems<Movie>> getUpcomingMovies() {

        if (upcomingMovies.getValue() == null) {
            refreshUpcomingMovies();
        }

        return upcomingMovies;
    }

    public void refreshData() {
        refreshNowPlayingMovies();
        refreshUpcomingMovies();
        refreshTopRatedMovies();
        refreshPopularMovies();
    }
}
