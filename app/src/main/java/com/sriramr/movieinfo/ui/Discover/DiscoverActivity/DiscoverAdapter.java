package com.sriramr.movieinfo.ui.Discover.DiscoverActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sriramr.movieinfo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.ViewHolder> {

    private SparseArray<String> genreList;
    private Context context;
    private DiscoverMovieClickListener mListener;
    public DiscoverAdapter(Context context, DiscoverMovieClickListener listener){
        this.context = context;
        mListener = listener;
        genreList = new SparseArray<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v  = LayoutInflater.from(context).inflate(R.layout.item_discover,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int genreId = genreList.keyAt(position);
        String title = genreList.get(genreId);
        holder.bind(title);
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public void setGenreList(SparseArray<String> genreList){
        this.genreList = genreList;
        notifyDataSetChanged();
    }

    public interface DiscoverMovieClickListener{
        void onClickItem(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.discover_title)
        TextView title;
        @BindView(R.id.discover_image)
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(String title){
            this.title.setText(title);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mListener.onClickItem(position);
        }
    }
}
