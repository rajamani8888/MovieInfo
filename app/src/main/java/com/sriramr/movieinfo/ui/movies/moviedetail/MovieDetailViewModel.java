package com.sriramr.movieinfo.ui.movies.moviedetail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sriramr.movieinfo.database.DatabaseRepository;
import com.sriramr.movieinfo.database.MovieWrapper;
import com.sriramr.movieinfo.models.NetworkItem;
import com.sriramr.movieinfo.network.NetworkRepository;
import com.sriramr.movieinfo.ui.movies.moviedetail.models.Cast;
import com.sriramr.movieinfo.ui.movies.moviedetail.models.Genre;
import com.sriramr.movieinfo.ui.movies.moviedetail.models.MovieDetailResponse;
import com.sriramr.movieinfo.ui.people.peoplepopular.models.PopularPeople;
import com.sriramr.movieinfo.utils.Status;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MovieDetailViewModel extends AndroidViewModel {

    private MutableLiveData<NetworkItem<MovieDetailResponse>> movieDetailResponse;
    private String movieId, movieTitle;
    private ArrayList<PopularPeople> casts;
    private CompositeDisposable compositeDisposable;
    private MovieWrapper movieFromDb;

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        movieDetailResponse = new MutableLiveData<>();
        compositeDisposable = new CompositeDisposable();
        if (movieFromDb == null) movieFromDb = DatabaseRepository.getMovieWithId(movieId);
    }

    public void init(String movieId, String movieTitle) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
    }

    public MutableLiveData<NetworkItem<MovieDetailResponse>> getMovieDetailResponse() {

        if (movieDetailResponse.getValue() == null) {
            refreshData();
        }

        return movieDetailResponse;
    }

    public void refreshData() {
        compositeDisposable.add(
                NetworkRepository.getMovieDetails(movieId).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(movie -> movieDetailResponse.setValue(new NetworkItem<>(Status.SUCCESS, movie)),
                                throwable -> movieDetailResponse.setValue(new NetworkItem<>(Status.FAILURE)))
        );
    }

    public ArrayList<PopularPeople> getCasts() {
        if (casts == null) {
            this.casts = new ArrayList<>();
            List<Cast> casts = movieDetailResponse.getValue().getItem().getCredits().getCast();
            for (Cast c : casts) {
                PopularPeople p = new PopularPeople();
                p.setName(c.getName());
                p.setId(c.getId());
                p.setProfilePath(c.getProfilePath());
                this.casts.add(p);
                Timber.e("Added %s", c.getName());
            }
        }
        return casts;
    }

    public MovieWrapper getMovieFromDb() {

        if (movieFromDb == null) {
            // this means that this particular movie isnt present in the database. Create a new movie
            String moviePoster = movieDetailResponse.getValue().getItem().getPosterPath();
            List<Genre> movieGenre = movieDetailResponse.getValue().getItem().getGenres();
            StringBuilder genre = new StringBuilder();
            for (Genre g : movieGenre) {
                genre.append(g.getName());
                genre.append(",");
            }
            double movieVoteAverage = movieDetailResponse.getValue().getItem().getVoteAverage();
            movieFromDb = new MovieWrapper(movieTitle, movieId, moviePoster, genre.toString(), String.valueOf(movieVoteAverage));
        }

        return movieFromDb;
    }

    public void setMovie(MovieWrapper movie) {
        this.movieFromDb = movie;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

}
