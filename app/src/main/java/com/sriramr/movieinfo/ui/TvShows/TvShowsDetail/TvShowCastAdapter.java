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
import com.sriramr.movieinfo.ui.TvShows.TvShowsDetail.Model.Cast;
import com.sriramr.movieinfo.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvShowCastAdapter extends RecyclerView.Adapter<TvShowCastAdapter.ViewHolder> {

    private List<Cast> cast;
    private Context context;
    private CastClickListener mClickListener;

    public TvShowCastAdapter(Context context, CastClickListener castClickListener){
        this.context = context;
        mClickListener = castClickListener;
        cast = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_cast,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cast c = cast.get(position);
        holder.bind(c);
    }

    public interface CastClickListener{
        void onCastClicked(Cast cast);
    }

    @Override
    public int getItemCount() {
        return cast.isEmpty() ?0 :cast.size();
    }

    public void setCast(List<Cast> cast){
        this.cast = cast;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.cast_image)
        ImageView castImage;
        @BindView(R.id.cast_title)
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        void bind(Cast c){
            Picasso.with(context).load(AppConstants.IMAGE_BASE_URL+AppConstants.POSTER_SIZE+c.getProfilePath())
                    .centerCrop()
                    .fit()
                    .into(castImage);
            title.setText(c.getName());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Cast c = cast.get(position);
            mClickListener.onCastClicked(c);
        }
    }
}
