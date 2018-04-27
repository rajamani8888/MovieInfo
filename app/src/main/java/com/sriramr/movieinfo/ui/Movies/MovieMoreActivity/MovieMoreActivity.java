package com.sriramr.movieinfo.ui.Movies.MovieMoreActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sriramr.movieinfo.Network.MovieService;
import com.sriramr.movieinfo.Network.NetworkService;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.ui.Movies.MovieDetailActivity.MovieDetailActivity;
import com.sriramr.movieinfo.ui.Movies.MovieListActivity.Models.Movie;
import com.sriramr.movieinfo.ui.Movies.MovieListActivity.Models.MovieListResponse;
import com.sriramr.movieinfo.utils.AppConstants;
import com.sriramr.movieinfo.utils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MovieMoreActivity extends AppCompatActivity implements MovieListAdapter.MoreMoviesClickListener {

    private static final String TAG = "MovieMoreActivity";

    @BindView(R.id.toolbar_activity_more)
    Toolbar toolbarActivityMore;
    @BindView(R.id.rv_movie_more)
    RecyclerView rvMovieMore;
    @BindView(R.id.pb_more_movie)
    ProgressBar progressBar;

    MovieListAdapter movieListAdapter;
    MovieService movieService;

    String movieTag;

    Call<MovieListResponse> movieCall;

    int page = 1;

    ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_more);
        ButterKnife.bind(this);

        toolbarActivityMore.setTitle("Movie");
        setSupportActionBar(toolbarActivityMore);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbarActivityMore.setTitleTextColor(Color.WHITE);

        movieTag = "";

        movies = new ArrayList<>();

        Intent i = getIntent();
        if (i != null) {
            movieTag = i.getStringExtra("TAG");
        } else {
            Toast.makeText(this, "Please Close the app and try again", Toast.LENGTH_SHORT).show();
            finish();
        }

        Timber.v("MovieTag: %s", movieTag);

        movieService = NetworkService.getService(this);

        progressBar.setVisibility(View.VISIBLE);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvMovieMore.setLayoutManager(layoutManager);
        rvMovieMore.setHasFixedSize(true);
        rvMovieMore.setItemAnimator(new DefaultItemAnimator());
        movieListAdapter = new MovieListAdapter(this, this);
        rvMovieMore.setAdapter(movieListAdapter);

        getDataFromApi(page);

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Timber.v("Page%s", page);
                ++page;
                getDataFromApi(page);
            }
        };

        rvMovieMore.addOnScrollListener(scrollListener);
    }


    private void getDataFromApi(int page) {

        switch (this.movieTag) {
            case AppConstants.MOVIES_TAG_NOW_PLAYING:
                movieCall = movieService.getNowPlayingMovies(page, AppConstants.API_KEY);
                break;
            case AppConstants.MOVIES_TAG_POPULAR:
                movieCall = movieService.getPopularMovies(page, AppConstants.API_KEY);
                break;
            case AppConstants.MOVIES_TAG_TOP_RATED:
                movieCall = movieService.getTopRatedMovies(page, AppConstants.API_KEY);
                break;
            case AppConstants.MOVIES_TAG_UPCOMING:
                movieCall = movieService.getUpcomingMovies(page, AppConstants.API_KEY);
                break;
            default:
                Toast.makeText(this, "Error. Please restart the app", Toast.LENGTH_SHORT).show();
                return;
        }

        movieCall.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.code() == 200) {
                    movies.addAll(response.body().getResults());
                    movieListAdapter.setData(movies);
                } else {
                    Timber.e(response.errorBody().toString());
                    Toast.makeText(MovieMoreActivity.this, "Check your Internet Connection and try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                Toast.makeText(MovieMoreActivity.this, "Check your internet connection and try again. If the error persists, Contact the developer", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public void onMovieClicked(Movie movie) {
        Timber.tag(TAG).v("Item clicked");
        Intent i = new Intent(MovieMoreActivity.this, MovieDetailActivity.class);
        i.putExtra(AppConstants.MOVIE_ID, String.valueOf(movie.getId()));
        startActivity(i);
    }

    @Override
    public void onPopupClicked(View v, int position) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_movie, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.movie_watched:
                    Toast.makeText(MovieMoreActivity.this, "Added " + movies.get(position).getTitle() + " to watched", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.movie_watch_later:
                    Toast.makeText(MovieMoreActivity.this, "Added " + movies.get(position).getTitle() + " to watch later", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;

        });

        popup.show();
    }


}
