package com.sriramr.movieinfo.ui.tvshows.showsmore;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.ui.tvshows.showsdetail.TvShowDetailActivity;
import com.sriramr.movieinfo.ui.tvshows.showslist.models.TvShow;
import com.sriramr.movieinfo.utils.AppConstants;
import com.sriramr.movieinfo.utils.EndlessRecyclerViewScrollListener;
import com.sriramr.movieinfo.utils.Status;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class TvShowsMoreActivity extends AppCompatActivity implements TvShowsMoreAdapter.TvShowItemClickListener {

    @BindView(R.id.toolbar_activity_more)
    Toolbar toolbarActivityMore;
    @BindView(R.id.pb_more_tv_show)
    ProgressBar pbMoreTvShow;
    @BindView(R.id.rv_tv_show_more)
    RecyclerView rvTvShowMore;

    TvShowsMoreAdapter showsMoreAdapter;
    private TvShowsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_shows_more);
        ButterKnife.bind(this);

        toolbarActivityMore.setTitle("TvShows");
        setSupportActionBar(toolbarActivityMore);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        if (i == null) {
            Toast.makeText(this, "Please Close the app and try again", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        mViewModel = ViewModelProviders.of(this).get(TvShowsViewModel.class);

        String movieTag = i.getStringExtra("TAG");
        mViewModel.init(movieTag);

        pbMoreTvShow.setVisibility(View.VISIBLE);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvTvShowMore.setLayoutManager(layoutManager);
        rvTvShowMore.setHasFixedSize(true);
        rvTvShowMore.setItemAnimator(new DefaultItemAnimator());
        showsMoreAdapter = new TvShowsMoreAdapter(this, this);
        rvTvShowMore.setAdapter(showsMoreAdapter);

        EndlessRecyclerViewScrollListener listener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                page++;
                Timber.v("Scroll Listener %s", page);
                mViewModel.refreshData();
            }
        };

        rvTvShowMore.addOnScrollListener(listener);

        observeDataFromApi();

    }

    private void observeDataFromApi() {
        mViewModel.getTvShows().observe(this, tvShowItem -> {
            if (tvShowItem == null || tvShowItem.getStatus() == Status.FAILURE) {
                Toast.makeText(this, "Error retrieving data from API", Toast.LENGTH_SHORT).show();
                return;
            }
            pbMoreTvShow.setVisibility(View.GONE);
            showsMoreAdapter.setData(tvShowItem.getItems());
        });
    }

    @Override
    public void onShowItemClicked(TvShow show) {
        int showId = show.getId();
        String title = show.getName();
        Intent i = new Intent(TvShowsMoreActivity.this, TvShowDetailActivity.class);
        i.putExtra(AppConstants.TV_SHOW_ID, String.valueOf(showId));
        i.putExtra(AppConstants.TV_SHOW_TITLE, title);
        startActivity(i);
    }

    @Override
    public void onPopupMenuClicked(View view, int position) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_movie, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> popupClick(item, position));
        popup.show();
    }

    public boolean popupClick(MenuItem item, int position) {
        List<TvShow> showList = Objects.requireNonNull(mViewModel.getTvShows().getValue()).getItems();
        switch (item.getItemId()) {
            case R.id.movie_watched:
                Toast.makeText(this, "Added " + showList.get(position).getName() + " to watched", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.movie_watch_later:
                Toast.makeText(this, "Added " + showList.get(position).getName() + " to watch later", Toast.LENGTH_SHORT).show();
                return true;
            default:
        }
        return false;
    }
}
