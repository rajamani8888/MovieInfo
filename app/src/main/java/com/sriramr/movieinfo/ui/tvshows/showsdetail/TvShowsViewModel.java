package com.sriramr.movieinfo.ui.tvshows.showsdetail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sriramr.movieinfo.models.NetworkItem;
import com.sriramr.movieinfo.network.NetworkRepository;
import com.sriramr.movieinfo.ui.tvshows.showsdetail.models.Cast;
import com.sriramr.movieinfo.ui.tvshows.showsdetail.models.Recommendations;
import com.sriramr.movieinfo.ui.tvshows.showsdetail.models.Seasons;
import com.sriramr.movieinfo.ui.tvshows.showsdetail.models.TvShowDetailResponse;
import com.sriramr.movieinfo.utils.Status;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TvShowsViewModel extends AndroidViewModel {

    private String tvShowId;
    private String tvShowTitle;
    private MutableLiveData<NetworkItem<TvShowDetailResponse>> tvShowDetails;
    private CompositeDisposable disposable;

    public TvShowsViewModel(@NonNull Application application) {
        super(application);
        tvShowDetails = new MutableLiveData<>();
        disposable = new CompositeDisposable();
    }

    public void init(String tvShowId, String tvShowTitle) {
        this.tvShowId = tvShowId;
        this.tvShowTitle = tvShowTitle;
    }

    private void refreshData() {
        disposable.add(
                NetworkRepository.getShowDetails(tvShowId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(tvShowDetail -> {
                            NetworkItem<TvShowDetailResponse> show = new NetworkItem<>(Status.SUCCESS);
                            show.setItem(tvShowDetail);
                            tvShowDetails.setValue(show);
                        }, throwable -> {
                            NetworkItem<TvShowDetailResponse> show = new NetworkItem<>(Status.FAILURE);
                            tvShowDetails.setValue(show);
                        })
        );
    }

    public MutableLiveData<NetworkItem<TvShowDetailResponse>> getTvShowDetails() {

        if (tvShowDetails.getValue() == null) {
            refreshData();
        }

        return tvShowDetails;
    }

    public List<Seasons> getSeasons() {
        if (tvShowDetails.getValue() != null) {
            return tvShowDetails.getValue().getItem().getSeasons();
        }
        return null;
    }

    public List<Cast> getCasts() {
        if (tvShowDetails.getValue() != null) {
            return tvShowDetails.getValue().getItem().getCredits().getCast();
        }
        return null;
    }

    public Recommendations getRecommendations() {
        if (tvShowDetails.getValue() != null) {
            return tvShowDetails.getValue().getItem().getRecommendations();
        }
        return null;
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }
}
