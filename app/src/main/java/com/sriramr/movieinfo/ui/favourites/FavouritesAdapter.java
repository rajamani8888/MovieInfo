package com.sriramr.movieinfo.ui.favourites;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.database.MovieWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {

    private Context context;
    private FavouritesItemClickListener mClickListener;

    List<MovieWrapper> movies;


    public FavouritesAdapter(Context context, FavouritesItemClickListener clickListener){
        this.context = context;
        this.mClickListener = clickListener;
        movies = new ArrayList<>();
    }

    public interface FavouritesItemClickListener{
        void onFavouritesItemClicked(MovieWrapper movieWrapper);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_favourites,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieWrapper movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void addMovies(List<MovieWrapper> movies){
        this.movies = movies;
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
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(MovieWrapper movie){
            Picasso.with(context).load(movie.getPoster())
                    .fit()
                    .centerCrop()
                    .into(poster);

            title.setText(movie.getTitle());

            genre.setText(movie.getGenre());

            vote.setText(movie.getVoteAverage());
        }

        @Override
        public void onClick(View v) {
            mClickListener.onFavouritesItemClicked(movies.get(getAdapterPosition()));
        }
    }
}
