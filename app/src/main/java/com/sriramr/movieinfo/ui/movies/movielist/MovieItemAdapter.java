package com.sriramr.movieinfo.ui.movies.movielist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.ui.movies.movielist.models.Movie;
import com.sriramr.movieinfo.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MovieItemAdapter extends RecyclerView.Adapter<MovieItemAdapter.ViewHolder> {

    private List<Movie> movies;

    private Context context;

    private ItemClickListener mClickListener;

    public MovieItemAdapter(Context context, ItemClickListener clickListener) {
        this.movies = new ArrayList<>();
        this.context = context;
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vh = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_small, parent, false);
        return new ViewHolder(vh);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);

        String title = movie.getTitle();
        String imagepath = movie.getPosterPath();

        String imageUrl = AppConstants.IMAGE_BASE_URL + AppConstants.POSTER_SIZE + imagepath;
        Timber.v(imagepath);
        Timber.v(imageUrl);
        holder.bind(title, imageUrl);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void changeItems(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClick(Movie movie);
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.small_movie_image)
        ImageView movieImage;
        @BindView(R.id.small_movie_title)
        TextView movieTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(String title, String imageUrl) {
            Picasso.with(context).load(imageUrl)
                    .centerCrop()
                    .fit()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(movieImage);

            movieTitle.setText(title);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Movie movie = movies.get(position);
            mClickListener.onItemClick(movie);
        }
    }

}
