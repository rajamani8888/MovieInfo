package com.sriramr.movieinfo.ui.Discover.DiscoverActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.utils.AppConstants;
import com.sriramr.movieinfo.utils.Utils;
import com.sriramr.movieinfo.ui.Discover.DiscoverDetailActivity.DiscoverDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class DiscoverMoviesFragment extends Fragment implements DiscoverAdapter.DiscoverMovieClickListener {

    private static SparseArray<String> genreMoviesArray;

    @BindView(R.id.discover_rv)
    RecyclerView discoverRv;

    Unbinder unbinder;

    DiscoverAdapter discoverAdapter;

    public static DiscoverMoviesFragment newInstance() {
        return new DiscoverMoviesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_discover_movies, container, false);
        genreMoviesArray = Utils.getMovieGenreList();
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RecyclerView.LayoutManager discoverLayoutManager = new GridLayoutManager(getContext(),2);
        discoverRv.setLayoutManager(discoverLayoutManager);

        discoverAdapter = new DiscoverAdapter(getContext(),this);
        discoverRv.setAdapter(discoverAdapter);

        discoverAdapter.setGenreList(genreMoviesArray);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClickItem(int position) {
        Integer genreId = genreMoviesArray.keyAt(position);
        String genreName = genreMoviesArray.get(genreId);
        String genreType = AppConstants.DISCOVER_MOVIE;
        Intent i = new Intent(getActivity(), DiscoverDetailActivity.class);
        i.putExtra(AppConstants.DISCOVER_GENRE_ID,String.valueOf(genreId));
        i.putExtra(AppConstants.DISCOVER_GENRE_TYPE,genreType);
        i.putExtra(AppConstants.DISCOVER_GENRE_NAME,genreName);
        startActivity(i);
        Toast.makeText(getActivity(), "Clicked "+ genreMoviesArray.get(genreMoviesArray.keyAt(position)), Toast.LENGTH_SHORT).show();
    }
}
