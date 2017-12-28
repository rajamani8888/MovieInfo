package com.sriramr.movieinfo.ui.TvShows.TvShows;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.ui.TvShows.TvShows.Models.TvShow;
import com.sriramr.movieinfo.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class TvItemAdapter extends RecyclerView.Adapter<TvItemAdapter.ViewHolder> {

    private List<TvShow> tvShows;
    private Context context;
    private TvShowItemClickListener mClickListener;

    public TvItemAdapter(Context context, TvShowItemClickListener clickListener) {
        this.context = context;
        mClickListener = clickListener;
        this.tvShows = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vh = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_small, parent, false);
        return new ViewHolder(vh);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TvShow show = tvShows.get(position);

        String title = show.getName();
        String imagepath = show.getPosterPath();

        String imageUrl = AppConstants.IMAGE_BASE_URL + AppConstants.POSTER_SIZE + imagepath ;
        Timber.v(imagepath);
        Timber.v(imageUrl);
        holder.bind(title,imageUrl);
    }

    @Override
    public int getItemCount() {
        if (tvShows.isEmpty()){
            return 0;
        }
        return 5;
    }

    public void changeItems(List<TvShow> shows){
        this.tvShows = shows;
        notifyDataSetChanged();
    }

    public List<TvShow> getMovies(){
        return tvShows;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // the ID is named as movie but in the context, it is used for TvShows only..
        @BindView(R.id.small_movie_image)
        ImageView showImage;
        @BindView(R.id.small_movie_title)
        TextView showTitle;

        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(String title, String imageUrl){
            Picasso.with(context).load(imageUrl)
                    .centerCrop()
                    .fit()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(showImage);

            showTitle.setText(title);


        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            TvShow show = tvShows.get(position);
            mClickListener.onItemClicked(show);
        }
    }



}
