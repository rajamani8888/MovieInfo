package com.sriramr.movieinfo.ui.tvshows.showsdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sriramr.movieinfo.R;

import com.sriramr.movieinfo.ui.tvshows.showsdetail.models.Recommendation;
import com.sriramr.movieinfo.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvShowRecommendationsAdapter extends RecyclerView.Adapter<TvShowRecommendationsAdapter.ViewHolder> {

    private List<Recommendation> recommendations;
    private Context context;
    private RecommendationClickListener mClickListener;

    public TvShowRecommendationsAdapter(Context context, RecommendationClickListener clickListener) {
        this.context = context;
        mClickListener = clickListener;
        recommendations = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_cast, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recommendation recommendation = recommendations.get(position);
        holder.bind(recommendation);
    }

    @Override
    public int getItemCount() {
        return recommendations.isEmpty() ? 0 : recommendations.size();
    }

    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
        notifyDataSetChanged();
    }

    public interface RecommendationClickListener {
        void onRecommendationItemClicked(Recommendation recommendation);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.cast_image)
        ImageView image;

        @BindView(R.id.cast_title)
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(Recommendation recommendations) {

            Picasso.with(context).load(AppConstants.IMAGE_BASE_URL + AppConstants.POSTER_SIZE + recommendations.getPosterPath())
                    .centerCrop().fit().into(image);
            title.setText(recommendations.getName());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Recommendation r = recommendations.get(position);
            mClickListener.onRecommendationItemClicked(r);
        }
    }
}
