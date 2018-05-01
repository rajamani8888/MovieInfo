package com.sriramr.movieinfo.ui.people.peopledetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.ui.people.peopledetail.models.CastItem;
import com.sriramr.movieinfo.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    private Context context;
    private List<CastItem> cast;
    private CastClickListener mClickListener;

    public CastAdapter(Context context, CastClickListener clickListener) {
        this.context = context;
        mClickListener = clickListener;
        cast = new ArrayList<>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_star_cast, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CastItem castItem = cast.get(position);
        holder.bind(castItem);
    }

    @Override
    public int getItemCount() {
        return cast.isEmpty() ? 0 : cast.size();
    }

    public void setCast(List<CastItem> cast) {
        this.cast = cast;
        notifyDataSetChanged();
    }

    public interface CastClickListener {
        void onCastClicked(CastItem cast);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.movie_image)
        ImageView movieImage;
        @BindView(R.id.movie_name)
        TextView movieName;
        @BindView(R.id.movie_cast)
        TextView movieCast;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(CastItem cast) {
            Picasso.with(context).load(AppConstants.IMAGE_BASE_URL + AppConstants.POSTER_SIZE + cast.getPosterPath())
                    .fit()
                    .centerCrop()
                    .into(movieImage);
            movieName.setText(cast.getTitle());
            movieCast.setText(cast.getCharacter());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            CastItem c = cast.get(position);
            mClickListener.onCastClicked(c);

        }
    }

}
