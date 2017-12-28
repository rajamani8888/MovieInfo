package com.sriramr.movieinfo.ui.TvShows.TvShowsMore;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.ui.TvShows.TvShows.Models.TvShow;
import com.sriramr.movieinfo.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvShowsMoreAdapter extends RecyclerView.Adapter<TvShowsMoreAdapter.ViewHolder> {

    private List<TvShow> shows;
    private Context context;
    private TvShowItemClickListener mClickListener;

    public TvShowsMoreAdapter(Context context, TvShowItemClickListener clickListener){
        shows = new ArrayList<>();
        this.mClickListener = clickListener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_movie_long,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        TvShow show = shows.get(position);
        holder.title.setText(show.getName());
        holder.ratings.setText(String.valueOf(show.getVoteAverage()));
        String posterUrl = AppConstants.IMAGE_BASE_URL + AppConstants.POSTER_SIZE + show.getPosterPath();

        Picasso.with(context).load(posterUrl)
                .centerCrop()
                .fit()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.image);


        holder.overflow.setOnClickListener(v -> showPopupMenu(holder.overflow,holder.getAdapterPosition()));
    }

    private void showPopupMenu(View view, int position) {
        mClickListener.onPopupMenuClicked(view,position);
    }

    public interface TvShowItemClickListener{
        void onShowItemClicked(TvShow show);
        void onPopupMenuClicked(View view,int position);
    }

    void setData(List<TvShow> shows){
        this.shows = shows;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (shows == null){
            return 0;
        }
        return shows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            TvShow s = shows.get(position);
            mClickListener.onShowItemClicked(s);
        }
    }

    private class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        int position;
        public MyMenuItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            return false;
        }
    }
}
