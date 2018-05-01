package com.sriramr.movieinfo.ui.people.peoplepopular;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sriramr.movieinfo.R;
import com.sriramr.movieinfo.ui.people.peoplepopular.models.PopularPeople;
import com.sriramr.movieinfo.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopularPeopleAdapter extends RecyclerView.Adapter<PopularPeopleAdapter.ViewHolder> {

    private Context context;
    private List<PopularPeople> popularPeople;
    private PopularPeopleClickListener mClickListener;

    public PopularPeopleAdapter(Context context, PopularPeopleClickListener clickListener) {
        this.context = context;
        mClickListener = clickListener;
        popularPeople = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_popular_people, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PopularPeople person = popularPeople.get(position);
        holder.bind(person);
    }

    @Override
    public int getItemCount() {
        return popularPeople.isEmpty() ? 0 : popularPeople.size();
    }

    public void addPeople(List<PopularPeople> popularPeople) {
        this.popularPeople = popularPeople;
        notifyDataSetChanged();
    }

    public interface PopularPeopleClickListener {
        void onPersonClicked(PopularPeople popularPeople);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.person_name)
        TextView personName;

        @BindView(R.id.person_image)
        ImageView personImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        void bind(PopularPeople person) {
            Picasso.with(context).load(AppConstants.IMAGE_BASE_URL + AppConstants.POSTER_SIZE + person.getProfilePath())
                    .noFade()
                    .centerCrop()
                    .fit()
                    .into(personImage);
            personName.setText(person.getName());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            PopularPeople p = popularPeople.get(position);
            mClickListener.onPersonClicked(p);
        }
    }
}
