package com.sriramr.movieinfo.ui.movies.moviemore;

import android.arch.lifecycle.ViewModelProviders;
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

import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.ui.movies.moviedetail.MovieDetailActivity;
import com.sriramr.movieinfo.ui.movies.movielist.models.Movie;
import com.sriramr.movieinfo.utils.AppConstants;
import com.sriramr.movieinfo.utils.EndlessRecyclerViewScrollListener;
import com.sriramr.movieinfo.utils.Status;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    private MovieMoreViewModel mViewModel;

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

        Intent i = getIntent();
        if (i == null) {
            Toast.makeText(this, "Please Close the app and try again", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        mViewModel = ViewModelProviders.of(this).get(MovieMoreViewModel.class);

        String movieTag = i.getStringExtra("TAG");
        mViewModel.init(movieTag);

        progressBar.setVisibility(View.VISIBLE);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvMovieMore.setLayoutManager(layoutManager);
        rvMovieMore.setHasFixedSize(true);
        rvMovieMore.setItemAnimator(new DefaultItemAnimator());
        movieListAdapter = new MovieListAdapter(this, this);
        rvMovieMore.setAdapter(movieListAdapter);

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mViewModel.refreshData();
            }
        };

        rvMovieMore.addOnScrollListener(scrollListener);

        observeDataFromApi();
    }

    private void observeDataFromApi() {
        mViewModel.getMovies().observe(this, movieItem -> {
            if (movieItem == null || movieItem.getStatus() == Status.FAILURE) {
                Toast.makeText(this, "Error getting data", Toast.LENGTH_SHORT).show();
                return;
            }
            progressBar.setVisibility(View.GONE);
            movieListAdapter.setData(movieItem.getItems());
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
                    Toast.makeText(MovieMoreActivity.this, "Added " + mViewModel.getMovies().getValue().getItems().get(position).getTitle() + " to watched", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.movie_watch_later:
                    Toast.makeText(MovieMoreActivity.this, "Added " + mViewModel.getMovies().getValue().getItems().get(position).getTitle() + " to watch later", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;

        });

        popup.show();
    }

}
