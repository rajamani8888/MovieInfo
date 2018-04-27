package com.sriramr.movieinfo.ui.TvShows.TvShowsDetail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.sriramr.movieinfo.Network.MovieService;
import com.sriramr.movieinfo.Network.NetworkService;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.ui.People.PeopleDetailActivity.PeopleDetailActivity;
import com.sriramr.movieinfo.ui.People.PopularPeopleActivity.Models.PopularPeople;
import com.sriramr.movieinfo.ui.People.PopularPeopleActivity.PopularPeopleActivity;
import com.sriramr.movieinfo.ui.TvShows.TvShowsDetail.Model.Cast;
import com.sriramr.movieinfo.ui.TvShows.TvShowsDetail.Model.Genres;
import com.sriramr.movieinfo.ui.TvShows.TvShowsDetail.Model.Recommendation;
import com.sriramr.movieinfo.ui.TvShows.TvShowsDetail.Model.Seasons;
import com.sriramr.movieinfo.ui.TvShows.TvShowsDetail.Model.TvShowDetailResponse;
import com.sriramr.movieinfo.utils.AppConstants;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class TvShowDetailActivity extends AppCompatActivity implements TvShowCastAdapter.CastClickListener, TvShowRecommendationsAdapter.RecommendationClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.detail_poster_icon)
    ImageView detailPosterIcon;
    @BindView(R.id.detail_title)
    TextView detailTitle;
    @BindView(R.id.detail_status)
    TextView detailStatus;
    @BindView(R.id.detail_genre)
    TextView detailGenre;
    @BindView(R.id.detail_votes)
    TextView detailVotes;
    @BindView(R.id.detail_plot)
    TextView detailPlot;
    @BindView(R.id.detail_release_date)
    TextView detailReleaseDate;
    @BindView(R.id.detail_director)
    TextView detailDirector;
    @BindView(R.id.detail_runtime)
    TextView detailRuntime;
    @BindView(R.id.detail_meta_score)
    TextView detailMetaScore;
    @BindView(R.id.detail_images_view)
    ImageView detailImagesView;
    @BindView(R.id.detail_images)
    RelativeLayout detailImages;
    @BindView(R.id.detail_videos_view)
    ImageView detailVideosView;
    @BindView(R.id.detail_videos)
    RelativeLayout detailVideos;
    @BindView(R.id.detail_rv_seasons)
    RecyclerView detailRvSeasons;
    @BindView(R.id.detail_cast_see_all)
    TextView detailCastSeeAll;
    @BindView(R.id.detail_rv_cast)
    RecyclerView detailRvCast;
    @BindView(R.id.detail_recommendations_see_all)
    TextView detailRecommendationsSeeAll;
    @BindView(R.id.detail_rv_recommendations)
    RecyclerView detailRvRecommendations;
    @BindView(R.id.tv_show_detail_scroll_image)
    ImageView tvShowDetailScrollImage;
    @BindView(R.id.detail_season_count)
    TextView detailSeasonCount;
    @BindView(R.id.detail_episodes_count)
    TextView detailEpisodesCount;

    String tvShowId = "";
    String showTitle = "";

    MovieService service;

    TvShowSeasonsAdapter seasonsAdapter;
    TvShowCastAdapter castAdapter;
    TvShowRecommendationsAdapter recommendationsAdapter;

    List<Recommendation> recommendations = new ArrayList<>();
    List<Cast> cast = new ArrayList<>();
    List<Seasons> seasons = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    // TODO add similar tv shows later on
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        if (i != null) {
            Bundle bundle = i.getExtras();
            if (bundle != null) {
                tvShowId = bundle.getString(AppConstants.TV_SHOW_ID);
                showTitle = bundle.getString(AppConstants.TV_SHOW_TITLE);
            }
        }

        if (Objects.equals(tvShowId, "")) {
            Toast.makeText(this, "Error.. Please try agin. If the error persists, contact the developer", Toast.LENGTH_SHORT).show();
            finish();
            // finish wont stop the code below from executing. Once finish() is called, the onCreate() is executed and then closed.
            return;
        }

        service = NetworkService.getService(this);

        //seasons rv
        RecyclerView.LayoutManager seasonsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        detailRvSeasons.setLayoutManager(seasonsLayoutManager);
        detailRvSeasons.setNestedScrollingEnabled(false);
        detailRvSeasons.setHasFixedSize(true);
        seasonsAdapter = new TvShowSeasonsAdapter(this);
        detailRvSeasons.setAdapter(seasonsAdapter);

        // recommendation rv
        final RecyclerView.LayoutManager recommendationLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        detailRvRecommendations.setLayoutManager(recommendationLayoutManager);
        detailRvRecommendations.setHasFixedSize(true);
        detailRvRecommendations.setNestedScrollingEnabled(false);
        recommendationsAdapter = new TvShowRecommendationsAdapter(this, this);
        detailRvRecommendations.setAdapter(recommendationsAdapter);

        // cast rv
        RecyclerView.LayoutManager castLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        detailRvCast.setLayoutManager(castLayoutManager);
        detailRvCast.setHasFixedSize(true);
        detailRvCast.setNestedScrollingEnabled(false);
        castAdapter = new TvShowCastAdapter(this, this);
        detailRvCast.setAdapter(castAdapter);

        detailCastSeeAll.setOnClickListener(view -> {
            ArrayList<PopularPeople> popularPeople = new ArrayList<>();
            for (Cast c : cast) {
                PopularPeople p = new PopularPeople();
                p.setName(c.getName());
                p.setId(c.getId());
                p.setProfilePath(c.getProfilePath());
                popularPeople.add(p);
            }

            Parcelable p = Parcels.wrap(popularPeople);

            Intent tvShowIntent = new Intent(TvShowDetailActivity.this, PopularPeopleActivity.class);
            tvShowIntent.putExtra(AppConstants.CAST_LIST, p);
            tvShowIntent.putExtra(AppConstants.TAG, AppConstants.MOVIE_CAST);
            startActivity(tvShowIntent);
        });

    }

    public void getDataFromApi() {
        Observable<TvShowDetailResponse> call = service.getDetailTvShow(tvShowId, AppConstants.API_KEY, AppConstants.TV_SHOW_APPEND_TO_RESPONSE);

        compositeDisposable.add(
                call.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::completeUI, throwable -> {
                            Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show();
                            Timber.e(throwable);
                        })
        );

    }

    private void completeUI(TvShowDetailResponse show) {

        // load image in collapsing toolbar
        Picasso.with(this).load(AppConstants.IMAGE_BASE_URL + AppConstants.BACKDROP_SIZE + show.getBackdropPath())
                .fit()
                .into(tvShowDetailScrollImage);

        // poster icon
        Picasso.with(this).load(AppConstants.IMAGE_BASE_URL + AppConstants.POSTER_SIZE + show.getPosterPath())
                .fit().centerCrop()
                .into(detailPosterIcon);

        //title
        detailTitle.setText(show.getName());

        // release status
        detailStatus.setText(show.getStatus());

        //genre
        StringBuilder genreBuilder = new StringBuilder();
        for (Genres g : show.getGenres()) {
            genreBuilder.append(g.getName());
            genreBuilder.append(", ");
        }
        String genre = genreBuilder.toString();
        detailGenre.setText(genre);

        // votes
        detailVotes.setText(String.valueOf(show.getVoteAverage()));

        //overview
        detailPlot.setText(show.getOverview());

        //release date
        detailReleaseDate.setText(show.getLastAirDate());

        // runtime
        detailRuntime.setText(String.valueOf(show.getEpisodeRunTime().get(0)));

        //metascore
        detailMetaScore.setText(String.valueOf(show.getVoteCount()));

        // episodes
        detailEpisodesCount.setText(String.valueOf(show.getNumberOfEpisodes()));

        // seasons
        detailSeasonCount.setText(String.valueOf(show.getNumberOfSeasons()));

        //images view
        Picasso.with(this).load(AppConstants.IMAGE_BASE_URL + AppConstants.POSTER_SIZE + show.getPosterPath())
                .centerCrop().fit().into(detailImagesView);

        // videos view
        // TODO improve this
        Picasso.with(this).load(AppConstants.IMAGE_BASE_URL + AppConstants.POSTER_SIZE + show.getPosterPath())
                .centerCrop().fit().into(detailVideosView);

        // seasons adapter
        seasons.addAll(show.getSeasons());
        seasonsAdapter.setSeasons(seasons);

        // cast adapter
        cast.addAll(show.getCredits().getCast());
        castAdapter.setCast(cast);

        // recommendations adapter
        recommendations.addAll(show.getRecommendations().getResults());
        recommendationsAdapter.setRecommendations(recommendations);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromApi();
    }

    @Override
    protected void onPause() {
        compositeDisposable.dispose();
        super.onPause();
    }

    @Override
    public void onCastClicked(Cast cast) {
        String id = String.valueOf(cast.getId());
        Intent i = new Intent(TvShowDetailActivity.this, PeopleDetailActivity.class);
        i.putExtra(AppConstants.STAR_ID, id);
        startActivity(i);
    }

    @Override
    public void onRecommendationItemClicked(Recommendation recommendation) {
        Intent i = new Intent(TvShowDetailActivity.this, TvShowDetailActivity.class);
        i.putExtra(AppConstants.TV_SHOW_ID, String.valueOf(recommendation.getId()));
        i.putExtra(AppConstants.TV_SHOW_TITLE, recommendation.getName());
        startActivity(i);
        finish();
    }
}
