package com.sriramr.movieinfo.ui.tvshows.showsmore;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sriramr.movieinfo.models.NetworkItems;
import com.sriramr.movieinfo.network.NetworkRepository;
import com.sriramr.movieinfo.ui.tvshows.showslist.models.TvShow;
import com.sriramr.movieinfo.ui.tvshows.showslist.models.TvShowsResponse;
import com.sriramr.movieinfo.utils.AppConstants;
import com.sriramr.movieinfo.utils.Status;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TvShowsViewModel extends AndroidViewModel {

    private String movieTag;
    private CompositeDisposable disposable;
    private int page = 0;
    private MutableLiveData<NetworkItems<TvShow>> tvShows;
    private NetworkItems<TvShow> showsItem;

    public TvShowsViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(String movieTag) {
        this.movieTag = movieTag;
        disposable = new CompositeDisposable();
        showsItem = new NetworkItems<>();
        tvShows = new MutableLiveData<>();
    }

    public void refreshData() {
        ++page;
        Observable<TvShowsResponse> call;
        switch (this.movieTag) {
            case AppConstants.TAG_TV_ON_THE_AIR:
                call = NetworkRepository.getOnAirTvShows(page);
                break;
            case AppConstants.TAG_TV_AIRING_TODAY:
                call = NetworkRepository.getAiringTodayShows(page);
                break;
            case AppConstants.TAG_TV_POPULAR:
                call = NetworkRepository.getPopularShows(page);
                break;
            case AppConstants.TAG_TV_TOP_RATED:
                call = NetworkRepository.getTopRatedShows(page);
                break;
            default:
                call = NetworkRepository.getTopRatedShows(page);
                break;
        }

        disposable.add(
                call.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(movieData -> {
                            showsItem.setStatus(Status.SUCCESS);
                            showsItem.getItems().addAll(movieData.getResults());
                            tvShows.setValue(showsItem);
                        }, throwable -> {
                            showsItem.setStatus(Status.FAILURE);
                            tvShows.setValue(showsItem);
                        })
        );

    }

    public LiveData<NetworkItems<TvShow>> getTvShows() {

        if (tvShows.getValue() == null) {
            refreshData();
        }

        return tvShows;
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }
}
