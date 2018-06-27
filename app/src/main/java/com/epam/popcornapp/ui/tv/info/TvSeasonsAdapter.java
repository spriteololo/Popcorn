package com.epam.popcornapp.ui.tv.info;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.epam.popcornapp.R;
import com.epam.popcornapp.glide.GlideApp;
import com.epam.popcornapp.ui.currentSeasonView.SeasonItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvSeasonsAdapter extends RecyclerView.Adapter<TvSeasonsAdapter.Holder> {

    private final List<SeasonItem> seasons;
    private boolean isExpanded = false;

    private final ItemClickListener listener;

    TvSeasonsAdapter(final ItemClickListener listener) {
        this.seasons = new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public Holder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_seasons_item, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        if (seasons.isEmpty()) {
            return;
        }

        GlideApp.with(holder.posterView.getContext())
                .load(seasons.get(position).getPosterPath())
                .defaultOptions()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.posterView);

        final int number = seasons.get(position).getSeasonNumber();
        holder.seasonNumberView.setText(number == 0 ?
                holder.seasonNumberView.getContext().getString(R.string.special_season) :
                holder.seasonNumberView.getContext().getString(R.string.season_name, number));
        holder.descriptionView.setText(seasons.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        if (isExpanded) {
            return seasons.size();
        } else {
            return 1;
        }
    }

    public void setSeasons(final List<SeasonItem> seasons) {
        if (seasons == null) {
            return;
        }

        this.seasons.clear();
        this.seasons.addAll(seasons);
        this.isExpanded = false;
        notifyDataSetChanged();
    }

    public boolean isShowSeasonButton() {
        return seasons.size() > 1;
    }

    public void expand() {
        if (!isExpanded) {
            this.isExpanded = true;
            notifyItemRangeInserted(1, seasons.size() - 1);
        }

    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.adapter_seasons_image_view)
        ImageView posterView;

        @BindView(R.id.adapter_seasons_name_text_view)
        TextView seasonNumberView;

        @BindView(R.id.adapter_seasons_description_text_view)
        TextView descriptionView;

        Holder(@NonNull final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            if (listener != null) {
                final SeasonItem item = seasons.get(getAdapterPosition());
                listener.onSeasonClicked(item.getSeasonNumber(), seasons);
            }
        }
    }

    interface ItemClickListener {
        void onSeasonClicked(int seasonNumber, List<SeasonItem> seasonItemList);
    }
}
