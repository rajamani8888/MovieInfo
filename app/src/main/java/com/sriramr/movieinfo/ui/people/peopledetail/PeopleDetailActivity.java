package com.sriramr.movieinfo.ui.people.peopledetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.sriramr.movieinfo.network.MovieService;
import com.sriramr.movieinfo.network.NetworkService;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.ui.movies.moviedetail.MovieDetailActivity;
import com.sriramr.movieinfo.ui.people.peopledetail.models.CastItem;
import com.sriramr.movieinfo.ui.people.peopledetail.models.PeopleDetailResponse;
import com.sriramr.movieinfo.ui.tvshows.showsdetail.TvShowDetailActivity;
import com.sriramr.movieinfo.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PeopleDetailActivity extends AppCompatActivity implements CastAdapter.CastClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.profile_image)
    ImageView profileImage;
    @BindView(R.id.profile_name)
    TextView profileName;
    @BindView(R.id.profile_dob)
    TextView profileDob;
    @BindView(R.id.profile_birthplace)
    TextView profileBirthplace;
    @BindView(R.id.profile_popularity)
    TextView profilePopularity;
    @BindView(R.id.profile_overview)
    TextView profileOverview;
    @BindView(R.id.profile_cast)
    RecyclerView rvProfileCast;

    MovieService service;
    CastAdapter castAdapter;

    String id = "";

    List<CastItem> cast = new ArrayList<>();
    @BindView(R.id.pb_people_detail)
    ProgressBar pbPeopleDetail;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // intent to get the ID to ping
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            id = bundle.getString(AppConstants.STAR_ID);
        } else {
            Toast.makeText(this, "Error. Please try again", Toast.LENGTH_SHORT).show();
            finish();
            // if you don't, everything below this will run and app will crash because you dont have an ID.
            return;
        }

        service = NetworkService.getService(this);

        //setting up recycler view
        RecyclerView.LayoutManager castLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvProfileCast.setLayoutManager(castLayoutManager);
        rvProfileCast.setHasFixedSize(true);
        rvProfileCast.setNestedScrollingEnabled(false);
        castAdapter = new CastAdapter(this, this);
        rvProfileCast.setAdapter(castAdapter);

        pbPeopleDetail.setVisibility(View.VISIBLE);

    }

    private void getDataFromApi() {
        Observable<PeopleDetailResponse> call = service.getPersonDetail(id, AppConstants.API_KEY, AppConstants.STAR_APPEND_TO_RESPONSE);
        compositeDisposable.add(
                call.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(peopleDetailResponse -> {
                            updateUI(peopleDetailResponse);
                            pbPeopleDetail.setVisibility(View.GONE);
                        }, throwable -> {
                            Toast.makeText(PeopleDetailActivity.this, "Error. Please Try again..", Toast.LENGTH_SHORT).show();
                            pbPeopleDetail.setVisibility(View.GONE);
                        })
        );

    }

    private void updateUI(PeopleDetailResponse response) {

        // profile image
        Picasso.with(this).load(AppConstants.IMAGE_BASE_URL + AppConstants.POSTER_SIZE + response.getProfilePath())
                .into(profileImage);

        profileName.setText(response.getName());

        profileDob.setText("Born on" + response.getBirthday());

        profileBirthplace.setText("Born at " + response.getPlaceOfBirth());

        profilePopularity.setText(String.valueOf(response.getPopularity()));

        profileOverview.setText(response.getBiography());

        cast = response.getCombinedCredits().getCast();
        castAdapter.setCast(cast);

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
    public void onCastClicked(CastItem cast) {
        if (Objects.equals(cast.getMediaType(), AppConstants.CAST_MEDIA_TYPE_MOVIE)) {
            Intent i = new Intent(PeopleDetailActivity.this, MovieDetailActivity.class);
            i.putExtra(AppConstants.MOVIE_ID, String.valueOf(cast.getId()));
            i.putExtra(AppConstants.MOVIE_TITLE, cast.getTitle());
            startActivity(i);
        } else {
            Intent i = new Intent(PeopleDetailActivity.this, TvShowDetailActivity.class);
            i.putExtra(AppConstants.TV_SHOW_ID, String.valueOf(cast.getId()));
            i.putExtra(AppConstants.TV_SHOW_TITLE, cast.getTitle());
            startActivity(i);
        }
    }
}
