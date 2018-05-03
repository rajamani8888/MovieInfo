package com.sriramr.movieinfo.ui.tvshows.showsmore;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.ui.tvshows.showslist.models.TvShow;
import com.sriramr.movieinfo.utils.AppConstants;
import com.sriramr.movieinfo.utils.DiffUtilsCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvShowsMoreAdapter extends RecyclerView.Adapter<TvShowsMoreAdapter.ViewHolder> {

    private List<TvShow> shows;
    private Context context;
    private TvShowItemClickListener mClickListener;
    private int lastPosition = -1;

    public TvShowsMoreAdapter(Context context, TvShowItemClickListener clickListener) {
        shows = new ArrayList<>();
        this.mClickListener = clickListener;
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
        TvShow show = shows.get(position);
        holder.title.setText(show.getName());
        holder.ratings.setText(String.valueOf(show.getVoteAverage()));
        String posterUrl = AppConstants.IMAGE_BASE_URL + AppConstants.POSTER_SIZE + show.getPosterPath();

        Picasso.with(context).load(posterUrl)
                .centerCrop()
                .fit()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.image);

        holder.overflow.setOnClickListener(v -> showPopupMenu(holder.overflow, holder.getAdapterPosition()));

        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_slide_bottom);
            viewToAnimate.setAnimation(animation);
            lastPosition = position;
        }
    }

    private void showPopupMenu(View view, int position) {
        mClickListener.onPopupMenuClicked(view, position);
    }

    void setData(List<TvShow> shows) {
        DiffUtilsCallback<TvShow> callback = new DiffUtilsCallback<>(shows, this.shows);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callback);

        diffResult.dispatchUpdatesTo(this);
        this.shows.clear();
        this.shows.addAll(shows);
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    public interface TvShowItemClickListener {
        void onShowItemClicked(TvShow show);

        void onPopupMenuClicked(View view, int position);
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
            TvShow s = shows.get(position);
            mClickListener.onShowItemClicked(s);
        }
    }

}
