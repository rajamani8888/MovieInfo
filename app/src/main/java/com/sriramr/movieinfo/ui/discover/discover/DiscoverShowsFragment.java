package com.sriramr.movieinfo.ui.discover.discover;

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
import com.sriramr.movieinfo.ui.discover.discoverdetail.DiscoverDetailActivity;
import com.sriramr.movieinfo.utils.AppConstants;
import com.sriramr.movieinfo.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DiscoverShowsFragment extends Fragment implements DiscoverAdapter.DiscoverMovieClickListener {

    @BindView(R.id.discover_rv)
    RecyclerView discoverRv;

    private Unbinder unbinder;
    private DiscoverAdapter discoverAdapter;
    private static SparseArray<String> genreShowsArray;

    public static DiscoverShowsFragment newInstance() {
        return new DiscoverShowsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_discover_movies, container, false);
        genreShowsArray = Utils.getTvShowGenreList();
        unbinder = ButterKnife.bind(this, v);

        RecyclerView.LayoutManager discoverLayoutManager = new GridLayoutManager(getContext(), 2);
        discoverRv.setLayoutManager(discoverLayoutManager);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        discoverAdapter = new DiscoverAdapter(getContext(), this);
        discoverRv.setAdapter(discoverAdapter);
        discoverAdapter.setGenreList(genreShowsArray);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClickItem(int position) {
        Integer genreId = genreShowsArray.keyAt(position);
        String genreName = genreShowsArray.get(genreId);
        String genreType = AppConstants.DISCOVER_SHOW;
        Intent i = new Intent(getActivity(), DiscoverDetailActivity.class);
        i.putExtra(AppConstants.DISCOVER_GENRE_ID, String.valueOf(genreId));
        i.putExtra(AppConstants.DISCOVER_GENRE_TYPE, genreType);
        i.putExtra(AppConstants.DISCOVER_GENRE_NAME, genreName);
        startActivity(i);
        Toast.makeText(getActivity(), "Clicked " + genreShowsArray.get(genreShowsArray.keyAt(position)), Toast.LENGTH_SHORT).show();
    }

}
