package com.sriramr.movieinfo.ui.Discover.DiscoverDetailActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.ui.Discover.DiscoverActivity.DiscoverAdapter;
import com.sriramr.movieinfo.utils.AppConstants;
import com.sriramr.movieinfo.ui.Discover.DiscoverDetailActivity.Models.DiscoverMovieItem;

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
        View v = LayoutInflater.from(context).inflate(R.layout.item_discover_detail,parent,false);
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

        @BindView(R.id.discover_detail_image)
        ImageView posterImage;
        @BindView(R.id.discover_detail_title)
        TextView title;
        @BindView(R.id.discover_detail_release_date)
        TextView releaseDate;
        @BindView(R.id.discover_detail_ratings)
        TextView ratings;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(this);
        }

        public void bind(DiscoverMovieItem movieItem){
            Picasso.with(context).load(AppConstants.IMAGE_BASE_URL+AppConstants.POSTER_SIZE+movieItem.getPosterPath())
                    .fit().centerCrop()
                    .into(posterImage);

            title.setText(movieItem.getTitle());

            releaseDate.setText(movieItem.getReleaseDate());

            ratings.setText(String.valueOf(movieItem.getVoteAverage()));



        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mClickListener.onDiscoverItemClicked(position);
        }
    }
}
