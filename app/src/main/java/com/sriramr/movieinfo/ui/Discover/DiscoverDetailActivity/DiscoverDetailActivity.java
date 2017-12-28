package com.sriramr.movieinfo.ui.Discover.DiscoverDetailActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.sriramr.movieinfo.Network.MovieService;
import com.sriramr.movieinfo.Network.NetworkService;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.ui.Movies.MovieDetailActivity.MovieDetailActivity;
import com.sriramr.movieinfo.ui.TvShows.TvShowsDetail.TvShowDetailActivity;
import com.sriramr.movieinfo.utils.AppConstants;
import com.sriramr.movieinfo.ui.Discover.DiscoverDetailActivity.Models.DiscoverMovieItem;
import com.sriramr.movieinfo.ui.Discover.DiscoverDetailActivity.Models.DiscoverMoviesResponse;
import com.sriramr.movieinfo.ui.Discover.DiscoverDetailActivity.Models.DiscoverShowItem;
import com.sriramr.movieinfo.ui.Discover.DiscoverDetailActivity.Models.DiscoverShowResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static android.view.View.GONE;


public class DiscoverDetailActivity extends AppCompatActivity implements DiscoverDetailAdapter.DiscoverItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.rv_discover)
    RecyclerView rvDiscover;

    DiscoverDetailAdapter discoverAdapter;

    MovieService service;

    String genreId;
    String genreType;
    String genreName;

    List<DiscoverMovieItem> movies = new ArrayList<>();
    List<DiscoverShowItem> shows = new ArrayList<>();

    int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent i = getIntent();
        if (i != null) {
            genreId = i.getStringExtra(AppConstants.DISCOVER_GENRE_ID);
            genreType = i.getStringExtra(AppConstants.DISCOVER_GENRE_TYPE);
            genreName = i.getStringExtra(AppConstants.DISCOVER_GENRE_NAME);
        } else {
            Toast.makeText(this, "Error.. Please try again", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        getSupportActionBar().setTitle(genreName);


        RecyclerView.LayoutManager discoverLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDiscover.setLayoutManager(discoverLayoutManager);
        rvDiscover.setHasFixedSize(true);
        discoverAdapter = new DiscoverDetailAdapter(this,this);
        rvDiscover.setAdapter(discoverAdapter);

        service = NetworkService.getService(this);

        progress.setVisibility(View.VISIBLE);

        if (Objects.equals(genreType, AppConstants.DISCOVER_MOVIE)) {
            getDiscoverMovies();
        } else {
            getDiscoverShows();
        }

    }

    void getDiscoverMovies() {

        Call<DiscoverMoviesResponse> call = service.getDiscoveredMovies(AppConstants.API_KEY, genreId, page);

        call.enqueue(new Callback<DiscoverMoviesResponse>() {
            @Override
            public void onResponse(Call<DiscoverMoviesResponse> call, Response<DiscoverMoviesResponse> response) {
                if (response.isSuccessful()) {
                    movies = response.body().getResults();
                    discoverAdapter.setMovies(movies);
                } else {
                    Toast.makeText(DiscoverDetailActivity.this, "Error.. Could not load movies.. Try again..", Toast.LENGTH_SHORT).show();
                }
                progress.setVisibility(GONE);
            }

            @Override
            public void onFailure(Call<DiscoverMoviesResponse> call, Throwable t) {
                progress.setVisibility(GONE);
                Toast.makeText(DiscoverDetailActivity.this, "Error.. Could not load movies.. Try again..", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void getDiscoverShows() {
        Call<DiscoverShowResponse> call = service.getDiscoverShows(AppConstants.API_KEY, genreId, page);

        call.enqueue(new Callback<DiscoverShowResponse>() {
            @Override
            public void onResponse(Call<DiscoverShowResponse> call, Response<DiscoverShowResponse> response) {
                if (response.isSuccessful()) {
                    List<DiscoverMovieItem> showsItems = new ArrayList<>();
                    shows = response.body().getResults();
                    for (DiscoverShowItem showItem : shows) {
                        DiscoverMovieItem show = new DiscoverMovieItem();
                        show.setTitle(showItem.getName());
                        show.setPosterPath(showItem.getPosterPath());
                        show.setVoteAverage(showItem.getVoteAverage());
                        show.setId(showItem.getId());
                        show.setReleaseDate(showItem.getFirstAirDate());
                        showsItems.add(show);
                    }
                    discoverAdapter.setMovies(showsItems);
                } else {
                    Toast.makeText(DiscoverDetailActivity.this, "Error. Please try again.", Toast.LENGTH_SHORT).show();
                    Timber.e(response.message());
                }
                progress.setVisibility(GONE);
            }

            @Override
            public void onFailure(Call<DiscoverShowResponse> call, Throwable t) {
                Toast.makeText(DiscoverDetailActivity.this, "Error. Please try again.", Toast.LENGTH_SHORT).show();
                Timber.e(t.getMessage());
                progress.setVisibility(GONE);
            }
        });

    }

    @Override
    public void onDiscoverItemClicked(int position) {
        if (Objects.equals(genreType, AppConstants.DISCOVER_MOVIE)) {
            Intent i = new Intent(DiscoverDetailActivity.this, MovieDetailActivity.class);
            i.putExtra(AppConstants.MOVIE_ID, String.valueOf(movies.get(position).getId()));
            i.putExtra(AppConstants.MOVIE_TITLE, movies.get(position).getTitle());
            startActivity(i);
        } else {
            Intent i = new Intent(DiscoverDetailActivity.this, TvShowDetailActivity.class);
            i.putExtra(AppConstants.TV_SHOW_ID, String.valueOf(shows.get(position).getId()));
            i.putExtra(AppConstants.TV_SHOW_TITLE, shows.get(position).getName());
            startActivity(i);
        }
    }
}
