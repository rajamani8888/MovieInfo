package com.sriramr.movieinfo.ui.discover.discoverdetail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sriramr.movieinfo.models.NetworkItems;
import com.sriramr.movieinfo.network.NetworkRepository;
import com.sriramr.movieinfo.ui.discover.discoverdetail.models.DiscoverMovieItem;
import com.sriramr.movieinfo.ui.discover.discoverdetail.models.DiscoverShowItem;
import com.sriramr.movieinfo.utils.Status;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class DiscoverDetailViewModel extends AndroidViewModel {

    private MutableLiveData<NetworkItems<DiscoverMovieItem>> discoverMovies;
    private MutableLiveData<NetworkItems<DiscoverMovieItem>> discoverShows;
    private String genreId, genreType, genreName;
    private int page = 0;
    private CompositeDisposable compositeDisposable;
    private NetworkItems<DiscoverMovieItem> moviesItem;
    private NetworkItems<DiscoverMovieItem> showsItem;

    public DiscoverDetailViewModel(@NonNull Application application) {
        super(application);
        discoverMovies = new MutableLiveData<>();
        discoverShows = new MutableLiveData<>();
        compositeDisposable = new CompositeDisposable();
        moviesItem = new NetworkItems<>();
        showsItem = new NetworkItems<>();
    }

    public void init(String genreId, String genreName, String genreType) {
        this.genreId = genreId;
        this.genreName = genreName;
        this.genreType = genreType;
    }

    public LiveData<NetworkItems<DiscoverMovieItem>> getDiscoverMovies() {

        if (discoverMovies.getValue() == null) {
            Timber.i("Loading new movies..");
            refreshMovies();
        }

        return discoverMovies;
    }

    public LiveData<NetworkItems<DiscoverMovieItem>> getDiscoverShows() {

        if (discoverShows.getValue() == null) {
            Timber.i("Loading new shows..");
            refreshShows();
        }

        return discoverShows;
    }

    public void refreshMovies() {
        // update page by 1 before refreshing everytime
        page += 1;
        compositeDisposable.add(NetworkRepository.getDiscoverMovies(genreId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(discoverMoviesResponse -> {
                    moviesItem.setStatus(Status.SUCCESS);
                    moviesItem.getListItems().addAll(discoverMoviesResponse.getResults());
                    discoverMovies.setValue(moviesItem);
                }, throwable -> {
                    moviesItem.setStatus(Status.FAILURE);
                    discoverMovies.setValue(moviesItem);
                    Timber.e(throwable);
                }));
    }

    public void refreshShows() {
        // update page by 1 before refreshing everytime
        page += 1;
        compositeDisposable.add(NetworkRepository.getDiscoverShows(genreId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(discoverShowResponse -> {
                    List<DiscoverMovieItem> showsItems = new ArrayList<>();
                    for (DiscoverShowItem showItem : discoverShowResponse.getResults()) {
                        DiscoverMovieItem show = new DiscoverMovieItem();
                        show.setTitle(showItem.getName());
                        show.setPosterPath(showItem.getPosterPath());
                        show.setVoteAverage(showItem.getVoteAverage());
                        show.setId(showItem.getId());
                        show.setReleaseDate(showItem.getFirstAirDate());
                        showsItems.add(show);
                    }
                    showsItem.setStatus(Status.SUCCESS);
                    showsItem.getListItems().addAll(showsItems);
                    discoverShows.setValue(showsItem);
                }, throwable -> {
                    showsItem.setStatus(Status.FAILURE);
                    discoverShows.setValue(showsItem);
                    Timber.e(throwable);
                })
        );
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getGenreType() {
        return genreType;
    }

    public void setGenreType(String genreType) {
        this.genreType = genreType;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
