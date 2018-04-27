package com.sriramr.movieinfo.ui.favourites;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.database.ShowsWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouriteShowsAdapter extends RecyclerView.Adapter<FavouriteShowsAdapter.ViewHolder> {

    private Context context;
    private FavouritesItemClickListener mClickListener;

    List<ShowsWrapper> shows;

    public FavouriteShowsAdapter(Context context, FavouritesItemClickListener clickListener) {
        this.context = context;
        this.mClickListener = clickListener;
        shows = new ArrayList<>();
    }

    public interface FavouritesItemClickListener {
        void onFavouritesItemClicked(ShowsWrapper movieWrapper);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_favourites, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShowsWrapper movie = shows.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    public void addShows(List<ShowsWrapper> shows) {
        this.shows = shows;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.favs_poster)
        ImageView poster;
        @BindView(R.id.favs_title)
        TextView title;
        @BindView(R.id.favs_genre)
        TextView genre;
        @BindView(R.id.favs_vote)
        TextView vote;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(ShowsWrapper shows) {

            String posterPath = shows.getPoster();
            if (!TextUtils.isEmpty(posterPath)) {
                Picasso.with(context).load(posterPath)
                        .fit()
                        .centerCrop()
                        .into(poster);
            }

            title.setText(shows.getTitle());

            genre.setText(shows.getGenre());

            vote.setText(shows.getVoteAverage());
        }

        @Override
        public void onClick(View v) {
            mClickListener.onFavouritesItemClicked(shows.get(getAdapterPosition()));
        }
    }
}
