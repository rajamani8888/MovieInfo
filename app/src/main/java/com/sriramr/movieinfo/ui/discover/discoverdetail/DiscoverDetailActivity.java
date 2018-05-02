package com.sriramr.movieinfo.ui.discover.discoverdetail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.ui.discover.discoverdetail.models.DiscoverMovieItem;
import com.sriramr.movieinfo.ui.movies.moviedetail.MovieDetailActivity;
import com.sriramr.movieinfo.ui.tvshows.showsdetail.TvShowDetailActivity;
import com.sriramr.movieinfo.utils.AppConstants;
import com.sriramr.movieinfo.utils.EndlessRecyclerViewScrollListener;
import com.sriramr.movieinfo.utils.Status;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class DiscoverDetailActivity extends AppCompatActivity implements DiscoverDetailAdapter.DiscoverItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.rv_discover)
    RecyclerView rvDiscover;

    DiscoverDetailAdapter discoverAdapter;

    private DiscoverDetailViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewModel = ViewModelProviders.of(this).get(DiscoverDetailViewModel.class);

        Intent i = getIntent();

        if (i == null) {
            Toast.makeText(this, "Error.. Please try again", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String genreId = i.getStringExtra(AppConstants.DISCOVER_GENRE_ID);
        String genreType = i.getStringExtra(AppConstants.DISCOVER_GENRE_TYPE);
        String genreName = i.getStringExtra(AppConstants.DISCOVER_GENRE_NAME);
        mViewModel.init(genreId, genreName, genreType);

        progress.setVisibility(View.VISIBLE);
        getSupportActionBar().setTitle(mViewModel.getGenreName());

        GridLayoutManager discoverLayoutManager = new GridLayoutManager(this, 3);
        rvDiscover.setLayoutManager(discoverLayoutManager);
        rvDiscover.setHasFixedSize(true);
        discoverAdapter = new DiscoverDetailAdapter(this, this);
        rvDiscover.setAdapter(discoverAdapter);

        if (Objects.equals(mViewModel.getGenreType(), AppConstants.DISCOVER_MOVIE)) {
            observeMovies();
        } else {
            observeShows();
        }

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(discoverLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (mViewModel.getGenreType().equals(AppConstants.DISCOVER_MOVIE)) {
                    mViewModel.refreshMovies();
                } else {
                    mViewModel.refreshShows();
                }
            }
        };

        rvDiscover.addOnScrollListener(scrollListener);

    }

    void observeMovies() {
        mViewModel.getDiscoverMovies().observe(this, discoverMovies -> {
            if (discoverMovies == null) {
                Timber.e("Error getting data from api.");
                return;
            }

            if (discoverMovies.getStatus() == Status.FAILURE) {
                Toast.makeText(this, "Error getting movies from the database", Toast.LENGTH_SHORT).show();
                return;
            }
            progress.setVisibility(View.GONE);
            List<DiscoverMovieItem> movies = discoverMovies.getItems();
            discoverAdapter.setMovies(movies);
        });
    }

    void observeShows() {
        mViewModel.getDiscoverShows().observe(this, discoverShows -> {
            if (discoverShows == null) {
                Timber.e("Error getting data from api.");
                return;
            }
            if (discoverShows.getStatus() == Status.FAILURE) {
                Toast.makeText(this, "Error getting shows from the database", Toast.LENGTH_SHORT).show();
                return;
            }
            progress.setVisibility(View.GONE);
            List<DiscoverMovieItem> shows = discoverShows.getItems();
            discoverAdapter.setMovies(shows);
        });
    }

    @Override
    public void onDiscoverItemClicked(int position) {
        if (Objects.equals(mViewModel.getGenreType(), AppConstants.DISCOVER_MOVIE)) {
            Intent i = new Intent(DiscoverDetailActivity.this, MovieDetailActivity.class);
            List<DiscoverMovieItem> movies = mViewModel.getDiscoverMovies().getValue().getItems();
            i.putExtra(AppConstants.MOVIE_ID, String.valueOf(movies.get(position).getId()));
            i.putExtra(AppConstants.MOVIE_TITLE, movies.get(position).getTitle());
            startActivity(i);
        } else {
            Intent i = new Intent(DiscoverDetailActivity.this, TvShowDetailActivity.class);
            List<DiscoverMovieItem> shows = mViewModel.getDiscoverShows().getValue().getItems();
            i.putExtra(AppConstants.TV_SHOW_ID, String.valueOf(shows.get(position).getId()));
            i.putExtra(AppConstants.TV_SHOW_TITLE, shows.get(position).getTitle());
            startActivity(i);
        }
    }

}
