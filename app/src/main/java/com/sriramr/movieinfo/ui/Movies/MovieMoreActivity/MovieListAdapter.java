package com.sriramr.movieinfo.ui.Movies.MovieMoreActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.ui.Movies.MovieListActivity.Models.Movie;
import com.sriramr.movieinfo.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private List<Movie> movies;
    private Context context;
    private MoreMoviesClickListener mClickListener;

    public MovieListAdapter(Context context, MoreMoviesClickListener clickListener) {
        movies = new ArrayList<>();
        mClickListener = clickListener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_movie_long, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Movie movie = movies.get(position);

        holder.title.setText(movie.getTitle());
        holder.ratings.setText(String.valueOf(movie.getVoteAverage()));

        String posterUrl = AppConstants.IMAGE_BASE_URL + AppConstants.POSTER_SIZE + movie.getPosterPath();

        Picasso.with(context).load(posterUrl)
                .centerCrop()
                .fit()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.image);


        holder.overflow.setOnClickListener(v -> showPopupMenu(holder.overflow, holder.getAdapterPosition()));
    }

    private void showPopupMenu(View view, int position) {
        mClickListener.onPopupClicked(view, position);
    }

    void setData(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public interface MoreMoviesClickListener {
        void onMovieClicked(Movie movie);

        void onPopupClicked(View v, int position);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.movie_list_thumbnail)
        ImageView image;
        @BindView(R.id.movie_list_title)
        TextView title;
        @BindView(R.id.movie_list_ratings)
        TextView ratings;
        @BindView(R.id.movie_list_overflow)
        ImageView overflow;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Movie m = movies.get(position);
            mClickListener.onMovieClicked(m);
        }
    }

}
