package com.sriramr.movieinfo.ui.Movies.MovieDetailActivity;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sriramr.movieinfo.ui.Movies.MovieDetailActivity.Models.Cast;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailCastAdapter extends RecyclerView.Adapter<MovieDetailCastAdapter.ViewHolder> {

    private List<Cast> cast;
    private Context context;
    private CastItemClickListener mClickListener;

    public  MovieDetailCastAdapter(Context context, CastItemClickListener castItemClickListener){
        mClickListener = castItemClickListener;
        cast = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_cast,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cast movie = cast.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        if (cast.isEmpty())return 0;
        return 8;
    }

    public void setCast(List<Cast> cast){
        this.cast = cast;
        notifyDataSetChanged();
    }

    public interface CastItemClickListener{
        void onCastItemClick(Cast cast);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.cast_image)
        ImageView image;

        @BindView(R.id.cast_title)
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        void bind(Cast cast){
            Picasso.with(context)
                    .load(AppConstants.IMAGE_BASE_URL+AppConstants.POSTER_SIZE+cast.getProfilePath())
                    .centerCrop().fit().into(image);

            title.setText(cast.getName());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Cast c = cast.get(position);
            mClickListener.onCastItemClick(c);
        }
    }
}
