package com.sriramr.movieinfo.ui.discover.discoverdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.utils.AppConstants;
import com.sriramr.movieinfo.ui.discover.discoverdetail.models.DiscoverMovieItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoverDetailAdapter extends RecyclerView.Adapter<DiscoverDetailAdapter.ViewHolder> {

    private List<DiscoverMovieItem> movies;
    private Context context;
    private DiscoverItemClickListener mClickListener;

    public DiscoverDetailAdapter(Context context, DiscoverItemClickListener clickListener){
        this.context = context;
        mClickListener = clickListener;
        movies = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_movie_long,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DiscoverMovieItem movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.isEmpty() ? 0 : movies.size();
    }

    public void setMovies(List<DiscoverMovieItem> movies){
        this.movies = movies;
        notifyDataSetChanged();
    }

    public interface DiscoverItemClickListener{
        void onDiscoverItemClicked(int position);
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
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(this);
        }

        public void bind(DiscoverMovieItem movieItem){
            Picasso.with(context).load(AppConstants.IMAGE_BASE_URL+AppConstants.POSTER_SIZE+movieItem.getPosterPath())
                    .fit().centerCrop()
                    .into(image);

            title.setText(movieItem.getTitle());

            ratings.setText(String.valueOf(movieItem.getVoteAverage()));

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mClickListener.onDiscoverItemClicked(position);
        }
    }
}
