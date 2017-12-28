package com.sriramr.movieinfo.ui.Movies.MovieListActivity;

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
import com.sriramr.movieinfo.Network.NetworkService;
import com.sriramr.movieinfo.ui.Movies.MovieDetailActivity.MovieDetailActivity;
import com.sriramr.movieinfo.ui.Movies.MovieListActivity.Models.Movie;
import com.sriramr.movieinfo.ui.Movies.MovieListActivity.Models.MovieListResponse;
import com.sriramr.movieinfo.ui.Movies.MovieMoreActivity.MovieMoreActivity;
import com.sriramr.movieinfo.Network.MovieService;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.utils.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class MovieListFragment extends Fragment implements View.OnClickListener, MovieItemAdapter.ItemClickListener {

    @BindView(R.id.progressbar_nowplaying)
    ProgressBar progressbar;
    @BindView(R.id.more_now_playing_movies)
    Button moreNowPlayingMovies;
    @BindView(R.id.rv_movies_now_playing)
    RecyclerView rvMoviesNowPlaying;
    @BindView(R.id.more_popular_movies)
    Button morePopularMovies;
    @BindView(R.id.rv_movies_popular)
    RecyclerView rvMoviesPopular;
    @BindView(R.id.more_toprated_movies)
    Button moreTopratedMovies;
    @BindView(R.id.rv_movies_toprated)
    RecyclerView rvMoviesToprated;
    @BindView(R.id.more_upcoming_movies)
    Button moreUpcomingMovies;
    @BindView(R.id.rv_movies_upcoming)
    RecyclerView rvMoviesUpcoming;

    MovieItemAdapter nowplayingMoviesAdapter, popularMoviesAdapter,upcomingMoviesAdapter,topratedMoviesAdapter;

    MovieService movieService;

    Unbinder unbinder;

    RecyclerView.LayoutManager nowplaying_layout_manager, popular_layout_manager, upcoming_layout_manager, toprated_layout_manager;
    SnapHelper nowPlayingRVSnapHelper, popularRVSnapHelper, upcomingMoviesRVSnapHelper , topratedRVSnapAdapter;

    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie_list, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();

        initViews(context);

        movieService = NetworkService.getService(context);

        getDataFromApi(1);


    }

    private void getDataFromApi(int page) {


        Call<MovieListResponse> callNowPlaying = movieService.getNowPlayingMovies(page, AppConstants.API_KEY);
        Call<MovieListResponse> callPopular = movieService.getPopularMovies(page, AppConstants.API_KEY);
        Call<MovieListResponse> callTopRated = movieService.getTopRatedMovies(page, AppConstants.API_KEY);
        Call<MovieListResponse> callUpcoming = movieService.getUpcomingMovies(page,AppConstants.API_KEY);


        callNowPlaying.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                if (response.isSuccessful()) {
                    nowplayingMoviesAdapter.changeItems(response.body().getResults());
                }else{
                    Toast.makeText(context, "Error loading data..", Toast.LENGTH_SHORT).show();
                }
                progressbar.setVisibility(GONE);
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                Toast.makeText(context, "Error loading data..", Toast.LENGTH_SHORT).show();
            }
        });


        // call to get popular movies
        callPopular.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                if (response.isSuccessful()) {
                    popularMoviesAdapter.changeItems(response.body().getResults());
                }else{
                    Toast.makeText(context, "Error loading data..", Toast.LENGTH_SHORT).show();
                }
                progressbar.setVisibility(GONE);
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                Toast.makeText(context, "Error loading data..", Toast.LENGTH_SHORT).show();
            }
        });

        callTopRated.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                if (response.isSuccessful()) {
                    topratedMoviesAdapter.changeItems(response.body().getResults());
                }else{
                    Toast.makeText(context, "Error loading data..", Toast.LENGTH_SHORT).show();
                }
                progressbar.setVisibility(GONE);
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                Toast.makeText(context, "Error.. loading data", Toast.LENGTH_SHORT).show();
            }
        });

        callUpcoming.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                if (response.isSuccessful()) {
                    upcomingMoviesAdapter.changeItems(response.body().getResults());
                }else{
                    Toast.makeText(context, "Error loading data..", Toast.LENGTH_SHORT).show();
                }
                progressbar.setVisibility(GONE);
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                Toast.makeText(context, "Error loading data..", Toast.LENGTH_SHORT).show();
            }
        });

    }


//    private void initClickListeners() {
//
//        ItemClickSupport.addTo(rvMoviesUpcoming)
//                .setOnItemClickListener((recyclerView, position, v) -> itemClicked(recyclerView,position));
//
//        ItemClickSupport.addTo(rvMoviesNowPlaying)
//                .setOnItemClickListener((recyclerView, position, v) -> itemClicked(recyclerView,position));
//
//        ItemClickSupport.addTo(rvMoviesToprated)
//                .setOnItemClickListener((recyclerView, position, v) -> itemClicked(recyclerView,position));
//
//        ItemClickSupport.addTo(rvMoviesPopular)
//                .setOnItemClickListener((recyclerView, position, v) -> itemClicked(recyclerView,position));
//
//    }

    private void initViews(Context context){
        progressbar.setVisibility(View.VISIBLE);

        // More button listeners
        moreNowPlayingMovies.setOnClickListener(this);
        morePopularMovies.setOnClickListener(this);
        moreTopratedMovies.setOnClickListener(this);
        moreUpcomingMovies.setOnClickListener(this);

        /* step 2: setting nested scrolling to false because we want the toolbar to hide as we scroll down the nested scroll view
            THIS STEP CAN BE IGNORED DEPENDING UPON THE SITUATION
         */
        rvMoviesNowPlaying.setNestedScrollingEnabled(true);
        rvMoviesPopular.setNestedScrollingEnabled(true);
        rvMoviesToprated.setNestedScrollingEnabled(true);
        rvMoviesUpcoming.setNestedScrollingEnabled(true);

        /* step3 : initialising the layout manager depending upon how we want. Here we have a horizontally scrolling RV
            NOTE: WE CANNOT USE THE SAME INSTANCE OF LAYOUT MANAGER FOR ALL THE RV's EVEN TOUGH THEY ARE THE SAME
        */
        nowplaying_layout_manager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        popular_layout_manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
        upcoming_layout_manager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        toprated_layout_manager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);


        //step 4: setting the layout manager to the recycler view
        rvMoviesNowPlaying.setLayoutManager(nowplaying_layout_manager);
        rvMoviesPopular.setLayoutManager(popular_layout_manager);
        rvMoviesUpcoming.setLayoutManager(upcoming_layout_manager);
        rvMoviesToprated.setLayoutManager(toprated_layout_manager);

        // step 5: initialising snapper to help snap RV. OPTIONAL
        nowPlayingRVSnapHelper = new LinearSnapHelper();
        popularRVSnapHelper = new LinearSnapHelper();
        upcomingMoviesRVSnapHelper = new LinearSnapHelper();
        topratedRVSnapAdapter = new LinearSnapHelper();

        // optional but recommended if your items dont change dimensions in runtime
        rvMoviesNowPlaying.setHasFixedSize(true);
        rvMoviesPopular.setHasFixedSize(true);
        rvMoviesUpcoming.setHasFixedSize(true);
        rvMoviesToprated.setHasFixedSize(true);

        // step 6: attaching snapper to their respective recycler views
        nowPlayingRVSnapHelper.attachToRecyclerView(rvMoviesNowPlaying);
        popularRVSnapHelper.attachToRecyclerView(rvMoviesPopular);
        upcomingMoviesRVSnapHelper.attachToRecyclerView(rvMoviesUpcoming);
        topratedRVSnapAdapter.attachToRecyclerView(rvMoviesToprated);

        // setup adapters
        nowplayingMoviesAdapter = new MovieItemAdapter(context,this);
        popularMoviesAdapter = new MovieItemAdapter(context,this);
        topratedMoviesAdapter = new MovieItemAdapter(context,this);
        upcomingMoviesAdapter = new MovieItemAdapter(context,this);

        rvMoviesNowPlaying.setAdapter(nowplayingMoviesAdapter);
        rvMoviesPopular.setAdapter(popularMoviesAdapter);
        rvMoviesToprated.setAdapter(topratedMoviesAdapter);
        rvMoviesUpcoming.setAdapter(upcomingMoviesAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        String tag;
        switch (view.getId()){
            case R.id.more_now_playing_movies:
                tag = AppConstants.TAG_NOW_PLAYING_MOVIES;
                break;
            case R.id.more_popular_movies:
                tag = AppConstants.TAG_POPULAR_MOVIES;
                break;
            case R.id.more_toprated_movies:
                tag = AppConstants.TAG_TOP_RATED_MOVIES;
                break;
            case R.id.more_upcoming_movies:
                tag = AppConstants.TAG_UPCOMING_MOVIES;
                break;
            default:
                tag = AppConstants.TAG_DEFAULT;
                break;
        }
        Intent i = new Intent(getActivity(),MovieMoreActivity.class);
        i.putExtra("TAG",tag);
        startActivity(i);
    }

    @Override
    public void onItemClick(Movie movie) {
        Integer movieId = movie.getId();
        String title =  movie.getTitle();
        Intent i = new Intent(getActivity(),MovieDetailActivity.class);
        i.putExtra(AppConstants.MOVIE_ID,movieId.toString());
        i.putExtra(AppConstants.MOVIE_TITLE,title);
        startActivity(i);

    }
}
