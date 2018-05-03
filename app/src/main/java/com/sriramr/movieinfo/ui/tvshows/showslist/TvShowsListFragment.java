package com.sriramr.movieinfo.ui.tvshows.showslist;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.ui.tvshows.showsdetail.TvShowDetailActivity;
import com.sriramr.movieinfo.ui.tvshows.showslist.models.TvShow;
import com.sriramr.movieinfo.ui.tvshows.showsmore.TvShowsMoreActivity;
import com.sriramr.movieinfo.utils.AppConstants;
import com.sriramr.movieinfo.utils.Status;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TvShowsListFragment extends Fragment implements View.OnClickListener, TvShowItemClickListener {

    @BindView(R.id.tv_progressbar_airing_today)
    ProgressBar tvProgressbarAiringToday;
    @BindView(R.id.more_airing_today_tv)
    Button moreAiringToday;
    @BindView(R.id.rv_tv_airing_today)
    RecyclerView rvTvAiringToday;
    @BindView(R.id.more_on_the_air_tv)
    Button moreOnTheAirTv;
    @BindView(R.id.rv_tv_on_the_air)
    RecyclerView rvTvOnTheAir;
    @BindView(R.id.more_popular_tv)
    Button morePopularTv;
    @BindView(R.id.rv_tv_popular)
    RecyclerView rvTvPopular;
    @BindView(R.id.more_toprated_tv)
    Button moreTopratedTv;
    @BindView(R.id.rv_tv_toprated)
    RecyclerView rvTvToprated;
    Unbinder unbinder;

    RecyclerView.LayoutManager airing_today_layout_manager, on_the_air_layout_manager, popular_layout_manager, top_rated_layout_manager;
    TvItemAdapter on_the_air_adapter;
    TvItemAdapter popular_adapter;
    TvItemAdapter top_rated_adapter;
    TvItemAdapter airing_today_adapter;

    Context context;

    private TvShowsViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tv_shows_list, container, false);
        unbinder = ButterKnife.bind(this, v);
        mViewModel = ViewModelProviders.of(this).get(TvShowsViewModel.class);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        context = getActivity();

        initViews();

        observeDataFromApi();
    }

    private void observeDataFromApi() {

        mViewModel.getAiringTodayShows().observe(this, showItems -> {
            if (showItems == null || showItems.getStatus() == Status.FAILURE) {
                Toast.makeText(context, "Error getting data from API", Toast.LENGTH_SHORT).show();
                return;
            }
            airing_today_adapter.changeItems(showItems.getItems());
        });

        mViewModel.getOnTheAirShows().observe(this, showItems -> {
            if (showItems == null || showItems.getStatus() == Status.FAILURE) {
                Toast.makeText(context, "Error getting data from API", Toast.LENGTH_SHORT).show();
                return;
            }
            on_the_air_adapter.changeItems(showItems.getItems());
        });

        mViewModel.getPopularShows().observe(this, showItems -> {
            if (showItems == null || showItems.getStatus() == Status.FAILURE) {
                Toast.makeText(context, "Error getting data from API", Toast.LENGTH_SHORT).show();
                return;
            }
            popular_adapter.changeItems(showItems.getItems());
        });

        mViewModel.getTopRatedShows().observe(this, showItems -> {
            if (showItems == null || showItems.getStatus() == Status.FAILURE) {
                Toast.makeText(context, "Error getting data from API", Toast.LENGTH_SHORT).show();
                return;
            }
            top_rated_adapter.changeItems(showItems.getItems());
        });

    }

    private void initViews() {

        // more click listeners
        moreAiringToday.setOnClickListener(this);
        moreOnTheAirTv.setOnClickListener(this);
        morePopularTv.setOnClickListener(this);
        moreTopratedTv.setOnClickListener(this);

        tvProgressbarAiringToday.setVisibility(View.VISIBLE);

        /* step 2: setting nested scrolling to false because we want the toolbar to hide as we scroll down the nested scroll view
            THIS STEP CAN BE IGNORED DEPENDING UPON THE SITUATION
         */
        rvTvAiringToday.setNestedScrollingEnabled(true);
        rvTvOnTheAir.setNestedScrollingEnabled(true);
        rvTvPopular.setNestedScrollingEnabled(true);
        rvTvToprated.setNestedScrollingEnabled(true);

        /* step3 : initialising the layout manager depending upon how we want. Here we have a horizontally scrolling RV
           NOTE: WE CANNOT USE THE SAME INSTANCE OF LAYOUT MANAGER FOR ALL THE RV's EVEN TOUGH THEY ARE THE SAME
        */
        airing_today_layout_manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        on_the_air_layout_manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        popular_layout_manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        top_rated_layout_manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);


        //step 4: setting the layout manager to the recycler view
        rvTvAiringToday.setLayoutManager(airing_today_layout_manager);
        rvTvOnTheAir.setLayoutManager(on_the_air_layout_manager);
        rvTvPopular.setLayoutManager(popular_layout_manager);
        rvTvToprated.setLayoutManager(top_rated_layout_manager);

        // step 5: initialising snapper to help snap RV. OPTIONAL
        SnapHelper airingTodayRVSnapHelper = new LinearSnapHelper();
        SnapHelper onTheAirRVSnapHelper = new LinearSnapHelper();
        SnapHelper popularRvSnapHelper = new LinearSnapHelper();
        SnapHelper topRatedRVSnapAdapter = new LinearSnapHelper();

        // optional but recommended if your items dont change dimensions in runtime
        rvTvAiringToday.setHasFixedSize(true);
        rvTvOnTheAir.setHasFixedSize(true);
        rvTvPopular.setHasFixedSize(true);
        rvTvToprated.setHasFixedSize(true);

        // step 6: attaching snapper to their respective recycler views
        airingTodayRVSnapHelper.attachToRecyclerView(rvTvAiringToday);
        onTheAirRVSnapHelper.attachToRecyclerView(rvTvOnTheAir);
        popularRvSnapHelper.attachToRecyclerView(rvTvPopular);
        topRatedRVSnapAdapter.attachToRecyclerView(rvTvToprated);

        airing_today_adapter = new TvItemAdapter(context, this);
        on_the_air_adapter = new TvItemAdapter(context, this);
        popular_adapter = new TvItemAdapter(context, this);
        top_rated_adapter = new TvItemAdapter(context, this);

        rvTvAiringToday.setAdapter(airing_today_adapter);
        rvTvOnTheAir.setAdapter(on_the_air_adapter);
        rvTvPopular.setAdapter(popular_adapter);
        rvTvToprated.setAdapter(top_rated_adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        String tag;
        switch (view.getId()) {
            case R.id.more_airing_today_tv:
                tag = AppConstants.TAG_TV_AIRING_TODAY;
                break;
            case R.id.more_on_the_air_tv:
                tag = AppConstants.TAG_TV_ON_THE_AIR;
                break;
            case R.id.more_popular_tv:
                tag = AppConstants.TAG_TV_POPULAR;
                break;
            case R.id.more_toprated_tv:
                tag = AppConstants.TAG_TV_TOP_RATED;
                break;
            default:
                tag = AppConstants.TAG_TV_TOP_RATED;
                break;
        }
        Intent i = new Intent(getActivity(), TvShowsMoreActivity.class);
        i.putExtra("TAG", tag);
        startActivity(i);
    }

    @Override
    public void onItemClicked(TvShow show) {
        int showId = show.getId();
        String title = show.getName();
        Intent i = new Intent(context, TvShowDetailActivity.class);
        i.putExtra(AppConstants.TV_SHOW_ID, String.valueOf(showId));
        i.putExtra(AppConstants.TV_SHOW_TITLE, title);
        startActivity(i);
    }
}
