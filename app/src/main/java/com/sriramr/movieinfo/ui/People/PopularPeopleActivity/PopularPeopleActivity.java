package com.sriramr.movieinfo.ui.People.PopularPeopleActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.sriramr.movieinfo.Network.MovieService;
import com.sriramr.movieinfo.Network.NetworkService;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.ui.People.PeopleDetailActivity.PeopleDetailActivity;
import com.sriramr.movieinfo.ui.People.PopularPeopleActivity.Models.PopularPeople;
import com.sriramr.movieinfo.ui.People.PopularPeopleActivity.Models.PopularPeopleResponse;
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

public class PopularPeopleActivity extends AppCompatActivity implements PopularPeopleAdapter.PopularPeopleClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_popular_people)
    RecyclerView rvPopularPeople;

    MovieService service;

    PopularPeopleAdapter popularPeopleAdapter;

    List<PopularPeople> peopleList = new ArrayList<>();

    private static int PAGE = 1;

    String tag = "";
    Bundle b = null;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

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
        b = getIntent().getExtras();
        if (b != null) {
            Parcelable parcelPeople = getIntent().getParcelableExtra(AppConstants.CAST_LIST);
            peopleList = Parcels.unwrap(parcelPeople);
            tag = b != null ? b.getString(AppConstants.TAG) : null;
        } else {
            Toast.makeText(this, "Error loading. Please try again..", Toast.LENGTH_SHORT).show();
            finish();
            // return is required as we dont want to execute the code below and just want the activity to close.
            return;
        }

        Timber.e("TAG %s", tag);

        // We close the activity if we dont get a tag through the intent
        if (Objects.equals(tag, "") || tag == null) {
            Toast.makeText(this, "Error.. Please load again..", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        service = NetworkService.getService(this);

        RecyclerView.LayoutManager popularPeopleLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvPopularPeople.setLayoutManager(popularPeopleLayoutManager);
        rvPopularPeople.setHasFixedSize(true);
        rvPopularPeople.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        popularPeopleAdapter = new PopularPeopleAdapter(this, this);
        rvPopularPeople.setAdapter(popularPeopleAdapter);

    }

    // api call to get popular people
    private void makeApiCall(int page) {

        Observable<PopularPeopleResponse> call = service.getPopularPeople(AppConstants.API_KEY, page);

        compositeDisposable.add(
                call.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(popularPeopleResponse -> {
                            peopleList.addAll(popularPeopleResponse.getResults());
                            popularPeopleAdapter.addPeople(peopleList);
                        }, throwable -> {
                            Toast.makeText(PopularPeopleActivity.this, "Error loading list.", Toast.LENGTH_SHORT).show();
                        })
        );

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

    @Override
    protected void onResume() {
        super.onResume();
        // we need to execute the network call only if the tag is popular people. For rest, the data will be sent through the intent
        if (Objects.equals(tag, AppConstants.POPULAR_PEOPLE)) {
            peopleList = new ArrayList<>();
            makeApiCall(PAGE);
        } else {
            // if the tag isn't popular people , we have the list and we have to update the rv.
            Timber.e("Added cast from movie/show");
            Timber.e("Length %d", peopleList.size());
            popularPeopleAdapter.addPeople(peopleList);
        }
    }

    @Override
    protected void onPause() {
        compositeDisposable.dispose();
        super.onPause();
    }

}
