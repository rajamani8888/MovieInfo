package com.sriramr.movieinfo.ui.TvShows.TvShowsMore;

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

import com.sriramr.movieinfo.Network.MovieService;
import com.sriramr.movieinfo.Network.NetworkService;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.ui.TvShows.TvShows.Models.TvShow;
import com.sriramr.movieinfo.ui.TvShows.TvShows.Models.TvShowsResponse;
import com.sriramr.movieinfo.ui.TvShows.TvShowsDetail.TvShowDetailActivity;
import com.sriramr.movieinfo.utils.AppConstants;
import com.sriramr.movieinfo.utils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class TvShowsMoreActivity extends AppCompatActivity implements TvShowsMoreAdapter.TvShowItemClickListener {

    String movieTag = "";
    @BindView(R.id.toolbar_activity_more)
    Toolbar toolbarActivityMore;
    @BindView(R.id.pb_more_tv_show)
    ProgressBar pbMoreTvShow;
    @BindView(R.id.rv_tv_show_more)
    RecyclerView rvTvShowMore;

    List<TvShow> showList;
    int page = 1;
    TvShowsMoreAdapter showsMoreAdapter;
    MovieService service;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_shows_more);
        ButterKnife.bind(this);

        toolbarActivityMore.setTitle("Movie");
        setSupportActionBar(toolbarActivityMore);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        showList = new ArrayList<>();

        Intent i = getIntent();
        if (i != null) {
            movieTag = i.getStringExtra("TAG");
        } else {
            Toast.makeText(this, "Please Close the app and try again", Toast.LENGTH_SHORT).show();
            finish();
        }

        service = NetworkService.getService(this);

        pbMoreTvShow.setVisibility(View.VISIBLE);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvTvShowMore.setLayoutManager(layoutManager);
        rvTvShowMore.setHasFixedSize(true);
        rvTvShowMore.setItemAnimator(new DefaultItemAnimator());
        showsMoreAdapter = new TvShowsMoreAdapter(this, this);
        rvTvShowMore.setAdapter(showsMoreAdapter);

        getDataFromApi(page);

        EndlessRecyclerViewScrollListener listener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                page++;
                Timber.v("Scroll Listener %s", page);
                getDataFromApi(page);
            }
        };

        rvTvShowMore.addOnScrollListener(listener);

    }

    private void getDataFromApi(int page) {
        Observable<TvShowsResponse> call;
        switch (this.movieTag) {
            case AppConstants.TAG_TV_ON_THE_AIR:
                call = service.getOnTheAirTvShows(AppConstants.API_KEY, page);
                break;
            case AppConstants.TAG_TV_AIRING_TODAY:
                call = service.getAiringTodayTvShows(AppConstants.API_KEY, page);
                break;
            case AppConstants.TAG_TV_POPULAR:
                call = service.getPopularTv(AppConstants.API_KEY, page);
                break;
            case AppConstants.TAG_TV_TOP_RATED:
                call = service.getTopRatedTv(AppConstants.API_KEY, page);
                break;
            default:
                call = service.getTopRatedTv(AppConstants.API_KEY, page);
                break;
        }

        compositeDisposable.add(
                call.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(tvShowsResponse -> {
                            showList.addAll(tvShowsResponse.getResults());
                            showsMoreAdapter.setData(showList);
                            pbMoreTvShow.setVisibility(View.GONE);
                        }, throwable -> {
                            Timber.e(throwable);
                            Toast.makeText(TvShowsMoreActivity.this, "Error. Try again", Toast.LENGTH_SHORT).show();
                            finish();
                        })
        );

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
