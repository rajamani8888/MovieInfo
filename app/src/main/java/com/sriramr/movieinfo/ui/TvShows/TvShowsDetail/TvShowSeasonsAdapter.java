package com.sriramr.movieinfo.ui.TvShows.TvShowsDetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.ui.TvShows.TvShowsDetail.Model.Seasons;
import com.sriramr.movieinfo.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvShowSeasonsAdapter extends RecyclerView.Adapter<TvShowSeasonsAdapter.ViewHolder> {

    private List<Seasons> seasons;
    private Context context;

    public TvShowSeasonsAdapter(Context context) {
        this.context = context;
        seasons = new ArrayList<>();
    }

    @Override
    public TvShowSeasonsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_cast, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TvShowSeasonsAdapter.ViewHolder holder, int position) {
        Seasons season = seasons.get(position);
        holder.bind(season);
    }

    @Override
    public int getItemCount() {
        return seasons.isEmpty() ? 0 : seasons.size();
    }

    public void setSeasons(List<Seasons> seasons) {
        this.seasons = seasons;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cast_image)
        ImageView castImage;
        @BindView(R.id.cast_title)
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Seasons seasons) {
            Picasso.with(context).load(AppConstants.IMAGE_BASE_URL + AppConstants.POSTER_SIZE + seasons.getPosterPath())
                    .centerCrop()
                    .fit()
                    .into(castImage);

            String s = "Season " +
                    seasons.getSeasonNumber() +
                    "/ " +
                    seasons.getEpisodeCount() +
                    " Episodes";

            title.setText(s);
        }

    }
}
