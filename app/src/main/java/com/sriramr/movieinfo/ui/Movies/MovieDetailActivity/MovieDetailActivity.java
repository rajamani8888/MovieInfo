package com.sriramr.movieinfo.ui.Movies.MovieDetailActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.sriramr.movieinfo.Network.MovieService;
import com.sriramr.movieinfo.Network.NetworkService;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.database.DatabaseRepository;
import com.sriramr.movieinfo.database.MovieWrapper;
import com.sriramr.movieinfo.ui.Movies.MovieDetailActivity.Models.Cast;
import com.sriramr.movieinfo.ui.Movies.MovieDetailActivity.Models.Genre;
import com.sriramr.movieinfo.ui.Movies.MovieDetailActivity.Models.MovieDetailResponse;
import com.sriramr.movieinfo.ui.Movies.MovieDetailActivity.Models.Recommendation;
import com.sriramr.movieinfo.ui.People.PeopleDetailActivity.PeopleDetailActivity;
import com.sriramr.movieinfo.ui.People.PopularPeopleActivity.Models.PopularPeople;
import com.sriramr.movieinfo.ui.People.PopularPeopleActivity.PopularPeopleActivity;
import com.sriramr.movieinfo.utils.AppConstants;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener, MovieDetailCastAdapter.CastItemClickListener, MovieDetailRecommendationsAdapter.RecommendationClickListener {

    @BindView(R.id.movie_detail_scroll_image)
    ImageView detailScroll;
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
    @BindView(R.id.detail_budget)
    TextView detailBudget;
    @BindView(R.id.detail_revenue)
    TextView detailRevenue;
    @BindView(R.id.detail_images)
    RelativeLayout detailImages;
    @BindView(R.id.detail_videos)
    RelativeLayout detailVideos;
    @BindView(R.id.detail_cast_see_all)
    TextView detailCastSeeAll;
    @BindView(R.id.detail_rv_cast)
    RecyclerView detailRvCast;
    @BindView(R.id.detail_recommendations_see_all)
    TextView detailRecommendationsSeeAll;
    @BindView(R.id.detail_rv_recommendations)
    RecyclerView detailRvRecommendations;

    String movieId = "", movieTitle = "", moviePoster = "", movieGenre = "", movieVoteAverage = "";

    MovieService movieService;
    MovieDetailCastAdapter castAdapter;
    MovieDetailRecommendationsAdapter recommendationsAdapter;

    @BindView(R.id.detail_images_view)
    ImageView detailImagesView;
    @BindView(R.id.detail_videos_view)
    ImageView detailVideosView;

    List<Cast> casts;
    List<Recommendation> recommendations;
    @BindView(R.id.movie_detail_pb)
    ProgressBar movieDetailPb;

    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.movie_favs)
    Button movieFavs;
    @BindView(R.id.movie_watched)
    Button movieWatched;
    @BindView(R.id.movie_watch_later)
    Button movieWatchLater;

    private boolean isAnimShowed = false;

    private Animation alphaAnim;

    private MovieWrapper movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        casts = new ArrayList<>();
        recommendations = new ArrayList<>();

        // get extras from the previous activity
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                movieId = bundle.getString(AppConstants.MOVIE_ID);
                movieTitle = bundle.getString(AppConstants.MOVIE_TITLE);
            }
        }

        if (Objects.equals(movieId, "")) {
            Toast.makeText(this, "Error.. Please try again. If the error persists, contact the developer", Toast.LENGTH_SHORT).show();
            finish();
            // finish wont stop the code below from executing. Once finish() is called, the onCreate() is executed and then closed.
            return;
        }

        alphaAnim = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);

        setupViews();

        movieService = NetworkService.getService(this);
        movieDetailPb.setVisibility(View.VISIBLE);

        Call<MovieDetailResponse> movieDetailResponseCall = movieService.getMovieDetails(movieId, AppConstants.API_KEY, AppConstants.MOVIE_APPEND_TO_RESPONSE);

        movieDetailResponseCall.enqueue(new Callback<MovieDetailResponse>() {
            @Override
            public void onResponse(Call<MovieDetailResponse> call, Response<MovieDetailResponse> response) {
                if (response.isSuccessful()) {
                    completeUI(response.body());
                } else {
                    Toast.makeText(MovieDetailActivity.this, "Error loading..", Toast.LENGTH_SHORT).show();
                }
                movieDetailPb.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieDetailResponse> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, "Error loading..", Toast.LENGTH_SHORT).show();
                movieDetailPb.setVisibility(View.GONE);
            }
        });

        //clicks
        detailCastSeeAll.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        movie = DatabaseRepository.getMovieWithId(movieId);
    }

    private void setupViews() {
        // cast rv
        RecyclerView.LayoutManager castLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        detailRvCast.setLayoutManager(castLayoutManager);
        detailRvCast.setNestedScrollingEnabled(false);
        detailRvCast.setHasFixedSize(true);
        castAdapter = new MovieDetailCastAdapter(this, this);
        detailRvCast.setAdapter(castAdapter);

        final RecyclerView.LayoutManager recommendationsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        detailRvRecommendations.setLayoutManager(recommendationsLayoutManager);
        detailRvRecommendations.setNestedScrollingEnabled(false);
        detailRvRecommendations.setHasFixedSize(true);
        recommendationsAdapter = new MovieDetailRecommendationsAdapter(this, this);
        detailRvRecommendations.setAdapter(recommendationsAdapter);
    }

    private void completeUI(MovieDetailResponse movie) {
        // load image in the collapsing toolbar
        Picasso.with(this).load(AppConstants.IMAGE_BASE_URL + AppConstants.BACKDROP_SIZE + movie.getBackdropPath())
                .fit().into(detailScroll);

        // poster icon
        moviePoster = AppConstants.IMAGE_BASE_URL + AppConstants.POSTER_SIZE + movie.getPosterPath();
        Picasso.with(this).load(moviePoster)
                .fit().centerCrop().into(detailPosterIcon);

        // title
        detailTitle.setText(movie.getOriginalTitle());
        // release status
        detailStatus.setText(movie.getStatus());

        //genre
        StringBuilder genreBuffer = new StringBuilder();
        for (Genre g : movie.getGenres()) {
            genreBuffer.append(g.getName());
            genreBuffer.append(",");
        }
        movieGenre = genreBuffer.toString();
        detailGenre.setText(movieGenre);

        movieVoteAverage = String.valueOf(movie.getVoteAverage());

        //no of votes
        detailVotes.setText(String.valueOf(movie.getVoteCount()));

        //overview
        detailPlot.setText(movie.getOverview());

        //release date
        detailReleaseDate.setText(String.valueOf(movie.getReleaseDate()));
        //TODO director

        // runtime
        detailRuntime.setText(String.valueOf(movie.getVoteCount()));

        //meta score
        detailMetaScore.setText(String.valueOf(movie.getVoteAverage()));

        //budget
        detailBudget.setText(String.valueOf(movie.getBudget()));

        //revenue
        detailRevenue.setText(String.valueOf(movie.getBudget()));

        //image view
        Picasso.with(this).load(AppConstants.IMAGE_BASE_URL + AppConstants.POSTER_SIZE + movie.getPosterPath())
                .centerCrop().fit().into(detailImagesView);

        // video view TODO video view
        Picasso.with(this).load(AppConstants.IMAGE_BASE_URL + AppConstants.POSTER_SIZE + movie.getPosterPath())
                .centerCrop().fit().into(detailVideosView);

        // cast recycler view
        casts = movie.getCredits().getCast();
        castAdapter.setCast(casts);

        //recommendations rv
        recommendations = movie.getRecommendations().getResults();
        recommendationsAdapter.setRecommendations(recommendations);

    }

    @Override
    public void onClick(View v) {
        // we need to convert CAST to POPULAR PEOPLE. We only need name, profile path and ID.
        ArrayList<PopularPeople> popularPeople = new ArrayList<>();
        Bundle b = new Bundle();
        for (Cast c : casts) {
            PopularPeople p = new PopularPeople();
            p.setName(c.getName());
            p.setId(c.getId());
            p.setProfilePath(c.getProfilePath());
            popularPeople.add(p);
            Timber.e("Added %s", c.getName());
        }
        Parcelable people = Parcels.wrap(popularPeople);
        Timber.e("Length before  sendin list %d", popularPeople.size());
        Intent i = new Intent(MovieDetailActivity.this, PopularPeopleActivity.class);
        b.putParcelable(AppConstants.CAST_LIST, people);
        i.putExtra(AppConstants.TAG, AppConstants.MOVIE_CAST);
        i.putExtras(b);
        startActivity(i);
    }

    @Override
    public void onCastItemClick(Cast cast) {
        Intent i = new Intent(MovieDetailActivity.this, PeopleDetailActivity.class);
        String castId = String.valueOf(cast.getId());
        Timber.i(castId);
        i.putExtra(AppConstants.STAR_ID, castId);
        startActivity(i);
    }

    @Override
    public void onRecommendationsItemClick(Recommendation recommendation) {
        Intent i = new Intent(MovieDetailActivity.this, MovieDetailActivity.class);
        i.putExtra(AppConstants.MOVIE_ID, String.valueOf(recommendation.getId()));
        i.putExtra(AppConstants.MOVIE_TITLE, recommendation.getTitle());
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab_movies)
    void onFabClick() {
        if (isAnimShowed) {
            frame.setVisibility(View.GONE);
            isAnimShowed = false;
        } else {
            int x = (frame.getLeft() + frame.getRight()) / 2;
            int y = (frame.getTop() + frame.getBottom()) / 2;

            int start = 0;
            int end = Math.max(x, y);

            Animator anim = ViewAnimationUtils.createCircularReveal(frame, x, y, start, end);

            frame.setVisibility(View.VISIBLE);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    layout.setVisibility(View.VISIBLE);
                    isAnimShowed = true;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            anim.start();
        }
    }

    @OnClick({R.id.movie_favs, R.id.movie_watched, R.id.movie_watch_later})
    public void onViewClicked(View view) {
        if (movie == null){
            // this means that this particular movie isnt present in the database. Create a new movie
            movie = new MovieWrapper(movieTitle,movieId,moviePoster,movieGenre,movieVoteAverage);
            movie.setDefaults();
        }
        switch (view.getId()) {
            case R.id.movie_favs:
                if (movie.getIsFavourite() == 1){
                    // movie is already in favs.
                    Toast.makeText(this, "Already added in favourites", Toast.LENGTH_SHORT).show();
                    return;
                }
                movie.setIsFavourite(1);
                Toast.makeText(this, "Added to favourites", Toast.LENGTH_SHORT).show();
                break;
            case R.id.movie_watched:
                if (movie.getIsWatched() == 1){
                    // movie is already in watched.
                    Toast.makeText(this, "Already added in watched", Toast.LENGTH_SHORT).show();
                    return;
                }
                movie.setIsWatched(1);
                Toast.makeText(this, "Added to watched", Toast.LENGTH_SHORT).show();
                break;
            case R.id.movie_watch_later:
                if (movie.getIsWatchLater() == 1){
                    // movie is already in watch later.
                    Toast.makeText(this, "Already added in watch later list", Toast.LENGTH_SHORT).show();
                    return;
                }
                movie.setIsWatchLater(1);
                Toast.makeText(this, "Added to watch later", Toast.LENGTH_SHORT).show();
                break;
        }

        // inserting the movie changes into the database
        DatabaseRepository.insertMovies(movie);
    }

}
