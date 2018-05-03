package com.sriramr.movieinfo.ui.movies.moviemore;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sriramr.movieinfo.models.NetworkItems;
import com.sriramr.movieinfo.network.NetworkRepository;
import com.sriramr.movieinfo.ui.movies.movielist.models.Movie;
import com.sriramr.movieinfo.ui.movies.movielist.models.MovieListResponse;
import com.sriramr.movieinfo.utils.AppConstants;
import com.sriramr.movieinfo.utils.Status;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MovieMoreViewModel extends AndroidViewModel {

    private MutableLiveData<NetworkItems<Movie>> movies;
    private int page = 0;
    private String tag;
    private CompositeDisposable disposable;
    private NetworkItems<Movie> movieItem;

    public MovieMoreViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(String tag) {
        this.tag = tag;
        disposable = new CompositeDisposable();
        movies = new MutableLiveData<>();
        movieItem = new NetworkItems<>();
    }

    public MutableLiveData<NetworkItems<Movie>> getMovies() {

        if (movies.getValue() == null) {
            refreshData();
        }

        return movies;
    }

    public void refreshData() {
        ++page;
        Observable<MovieListResponse> movieCall;
        switch (tag) {
            case AppConstants.MOVIES_TAG_NOW_PLAYING:
                movieCall = NetworkRepository.getNowPlayingMovies(page);
                break;
            case AppConstants.MOVIES_TAG_POPULAR:
                movieCall = NetworkRepository.getPopularMovies(page);
                break;
            case AppConstants.MOVIES_TAG_TOP_RATED:
                movieCall = NetworkRepository.getTopRatedMovies(page);
                break;
            case AppConstants.MOVIES_TAG_UPCOMING:
                movieCall = NetworkRepository.getUpcomingMovies(page);
                break;
            default:
                return;
        }

        disposable.add(movieCall.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieListResponse -> {
                    movieItem.setStatus(Status.SUCCESS);
                    movieItem.getItems().addAll(movieListResponse.getMovies());
                    movies.setValue(movieItem);
                }, throwable -> {
                    Timber.e(throwable);
                    movieItem.setStatus(Status.FAILURE);
                    movies.setValue(movieItem);
                })
        );
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
