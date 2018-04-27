package com.sriramr.movieinfo.ui.favourites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.database.DatabaseRepository;
import com.sriramr.movieinfo.database.ShowsWrapper;
import com.sriramr.movieinfo.utils.AppConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class FavouriteShowsFragment extends Fragment implements FavouriteShowsAdapter.FavouritesItemClickListener {

    @BindView(R.id.fav_rv)
    RecyclerView favRv;
    Unbinder unbinder;

    private List<ShowsWrapper> shows;
    private String tag;

    private FavouriteShowsAdapter mAdapter;

    /**
     * Returns a new instance of this fragment for the given section [ Watched | Favourites | Watch Later ]
     */
    public static FavouriteShowsFragment newInstance(String tag) {
        FavouriteShowsFragment fragment = new FavouriteShowsFragment();
        Bundle args = new Bundle();
        args.putString(AppConstants.TAG, tag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        Bundle b = getArguments();
        if (b == null) {
            Timber.e("Bundle is null");
            getActivity().finish();
            return null;
        }
        if (b.containsKey(AppConstants.TAG)) {
            tag = b.getString(AppConstants.TAG);
        } else {
            Toast.makeText(getActivity(), "Error loading.. Please try again..", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        switch (tag) {
            case AppConstants.FAVOURITES:
                shows = DatabaseRepository.getFavouriteShows();
                break;
            case AppConstants.WATCH_LATER:
                shows = DatabaseRepository.getWatchLaterShows();
                break;
            case AppConstants.WATCHED:
                shows = DatabaseRepository.getWatchedShows();
                break;
            default:
                shows = DatabaseRepository.getFavouriteShows();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        favRv.setLayoutManager(layoutManager);
        favRv.setHasFixedSize(true);

        mAdapter = new FavouriteShowsAdapter(getActivity(), this);
        favRv.setAdapter(mAdapter);

        mAdapter.addShows(shows);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onFavouritesItemClicked(ShowsWrapper movieWrapper) {
        Toast.makeText(getActivity(), movieWrapper.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
