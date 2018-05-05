package com.sriramr.movieinfo.ui.people.peopledetail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.ui.movies.moviedetail.MovieDetailActivity;
import com.sriramr.movieinfo.ui.people.peopledetail.models.CastItem;
import com.sriramr.movieinfo.ui.people.peopledetail.models.PeopleDetailResponse;
import com.sriramr.movieinfo.ui.tvshows.showsdetail.TvShowDetailActivity;
import com.sriramr.movieinfo.utils.AppConstants;
import com.sriramr.movieinfo.utils.Status;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.pb_people_detail)
    ProgressBar pbPeopleDetail;

    CastAdapter castAdapter;

    private PeopleDetailViewModel mViewModel;

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
        if (bundle == null) {
            Toast.makeText(this, "Error. Please try again", Toast.LENGTH_SHORT).show();
            finish();
            // if you don't, everything below this will run and app will crash because you dont have an ID.
            return;
        }

        mViewModel = ViewModelProviders.of(this).get(PeopleDetailViewModel.class);

        String id = bundle.getString(AppConstants.STAR_ID);
        mViewModel.init(id);

        //setting up recycler view
        RecyclerView.LayoutManager castLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvProfileCast.setLayoutManager(castLayoutManager);
        rvProfileCast.setHasFixedSize(true);
        rvProfileCast.setNestedScrollingEnabled(false);
        castAdapter = new CastAdapter(this, this);
        rvProfileCast.setAdapter(castAdapter);

        pbPeopleDetail.setVisibility(View.VISIBLE);

        observeDataFromApi();

    }

    private void observeDataFromApi() {
        mViewModel.getPeopleDetailItem().observe(this, peopleDetailItem -> {
            if (peopleDetailItem == null || peopleDetailItem.getStatus() == Status.FAILURE) {
                Toast.makeText(this, "Error retrieving data from API", Toast.LENGTH_SHORT).show();
                return;
            }
            pbPeopleDetail.setVisibility(View.GONE);
            updateUI(peopleDetailItem.getItem());
        });
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

        castAdapter.setCast(response.getCombinedCredits().getCast());

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
