package com.sriramr.movieinfo.ui.tvshows.showslist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sriramr.movieinfo.models.NetworkItems;
import com.sriramr.movieinfo.network.NetworkRepository;
import com.sriramr.movieinfo.ui.tvshows.showslist.models.TvShow;
import com.sriramr.movieinfo.utils.Status;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class TvShowsViewModel extends AndroidViewModel {

    private MutableLiveData<NetworkItems<TvShow>> airingTodayShows, onTheAirShows, popularShows, topRatedShows;
    private CompositeDisposable disposable;

    public TvShowsViewModel(@NonNull Application application) {
        super(application);
        airingTodayShows = new MutableLiveData<>();
        onTheAirShows = new MutableLiveData<>();
        popularShows = new MutableLiveData<>();
        topRatedShows = new MutableLiveData<>();
        disposable = new CompositeDisposable();
    }

    private void refreshAiringTodayShows(int page) {
        disposable.add(
                NetworkRepository.getAiringTodayShows(page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(tvShowsResponse -> {
                            NetworkItems<TvShow> shows = new NetworkItems<>(Status.SUCCESS);
                            shows.setItems(tvShowsResponse.getResults());
                            airingTodayShows.setValue(shows);
                        }, throwable -> {
                            NetworkItems<TvShow> shows = new NetworkItems<>(Status.FAILURE);
                            airingTodayShows.setValue(shows);
                            Timber.e(throwable);

                        })
        );
    }

    private void refreshOnAirShows(int page) {
        disposable.add(
                NetworkRepository.getOnAirTvShows(page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(tvShowsResponse -> {
                            NetworkItems<TvShow> shows = new NetworkItems<>(Status.SUCCESS);
                            shows.setItems(tvShowsResponse.getResults());
                            onTheAirShows.setValue(shows);
                        }, throwable -> {
                            NetworkItems<TvShow> shows = new NetworkItems<>(Status.FAILURE);
                            onTheAirShows.setValue(shows);
                            Timber.e(throwable);

                        })
        );
    }

    private void refreshPopularShows(int page) {
        disposable.add(
                NetworkRepository.getPopularShows(page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(tvShowsResponse -> {
                            NetworkItems<TvShow> shows = new NetworkItems<>(Status.SUCCESS);
                            shows.setItems(tvShowsResponse.getResults());
                            popularShows.setValue(shows);
                        }, throwable -> {
                            NetworkItems<TvShow> shows = new NetworkItems<>(Status.FAILURE);
                            popularShows.setValue(shows);
                            Timber.e(throwable);

                        })
        );
    }

    private void refreshTopRatedShows(int page) {
        disposable.add(
                NetworkRepository.getTopRatedShows(page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(tvShowsResponse -> {
                            NetworkItems<TvShow> shows = new NetworkItems<>(Status.SUCCESS);
                            shows.setItems(tvShowsResponse.getResults());
                            topRatedShows.setValue(shows);
                        }, throwable -> {
                            NetworkItems<TvShow> shows = new NetworkItems<>(Status.FAILURE);
                            topRatedShows.setValue(shows);
                            Timber.e(throwable);

                        })
        );
    }

    public MutableLiveData<NetworkItems<TvShow>> getAiringTodayShows() {

        if (airingTodayShows.getValue() == null) {
            refreshAiringTodayShows(1);
        }

        return airingTodayShows;
    }


    public MutableLiveData<NetworkItems<TvShow>> getOnTheAirShows() {

        if (onTheAirShows.getValue() == null) {
            refreshOnAirShows(1);
        }

        return onTheAirShows;
    }

    public MutableLiveData<NetworkItems<TvShow>> getPopularShows() {

        if (popularShows.getValue() == null) {
            refreshPopularShows(1);
        }

        return popularShows;
    }

    public MutableLiveData<NetworkItems<TvShow>> getTopRatedShows() {

        if (topRatedShows.getValue() == null) {
            refreshTopRatedShows(1);
        }

        return topRatedShows;
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }
}
