package com.sriramr.movieinfo.ui.people.peoplepopular;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.ui.people.peopledetail.PeopleDetailActivity;
import com.sriramr.movieinfo.ui.people.peoplepopular.models.PopularPeople;
import com.sriramr.movieinfo.utils.AppConstants;
import com.sriramr.movieinfo.utils.EndlessRecyclerViewScrollListener;
import com.sriramr.movieinfo.utils.Status;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopularPeopleActivity extends AppCompatActivity implements PopularPeopleAdapter.PopularPeopleClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_popular_people)
    RecyclerView rvPopularPeople;
    @BindView(R.id.pb_popular_people)
    ProgressBar pbPopularPeople;

    PopularPeopleAdapter popularPeopleAdapter;

    private PopularPeopleViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_people);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Popular People");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Get Tag and display data accordingly.
        Intent i = getIntent();
        Bundle b = i.getExtras();

        if (b == null) {
            Toast.makeText(this, "Error loading. Please try again..", Toast.LENGTH_SHORT).show();
            finish();
            // return is required as we dont want to execute the code below and just want the activity to close.
            return;
        }

        mViewModel = ViewModelProviders.of(this).get(PopularPeopleViewModel.class);

        Parcelable parcelPeople = getIntent().getParcelableExtra(AppConstants.CAST_LIST);
        ArrayList<PopularPeople> peopleList = Parcels.unwrap(parcelPeople);
        String tag = b.getString(AppConstants.TAG);

        // We close the activity if we dont get a tag through the intent
        if (Objects.equals(tag, "") || tag == null) {
            Toast.makeText(this, "Error.. Please load again..", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        mViewModel.init(tag);

        LinearLayoutManager popularPeopleLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvPopularPeople.setLayoutManager(popularPeopleLayoutManager);
        rvPopularPeople.setHasFixedSize(true);
        rvPopularPeople.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        popularPeopleAdapter = new PopularPeopleAdapter(this, this);
        rvPopularPeople.setAdapter(popularPeopleAdapter);

        // we need to execute the network call only if the tag is popular people. For rest, the data will be sent through the intent
        if (!Objects.equals(tag, AppConstants.POPULAR_PEOPLE)) {
            // if the tag isn't popular people , we have the list and we have to update the rv.
            mViewModel.setPeople(peopleList);
        }

        pbPopularPeople.setVisibility(View.VISIBLE);
        observeData();

        if (mViewModel.getTag().equals(AppConstants.POPULAR_PEOPLE)) {
            EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(popularPeopleLayoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    mViewModel.refreshData();
                }
            };

            rvPopularPeople.addOnScrollListener(scrollListener);
        }

    }

    private void observeData() {
        mViewModel.getPopularPeople().observe(this, popularPeopleItem -> {
            if (popularPeopleItem == null || popularPeopleItem.getStatus() == Status.FAILURE) {
                Toast.makeText(this, "Error getting data.", Toast.LENGTH_SHORT).show();
                return;
            }
            pbPopularPeople.setVisibility(View.GONE);
            popularPeopleAdapter.addPeople(popularPeopleItem.getItems());
        });
    }

    @Override
    public void onPersonClicked(PopularPeople popularPeople) {
        Intent i = new Intent(PopularPeopleActivity.this, PeopleDetailActivity.class);
        i.putExtra(AppConstants.STAR_ID, String.valueOf(popularPeople.getId()));
        startActivity(i);
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
