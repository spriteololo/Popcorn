package com.epam.popcornapp.ui.tv.season;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.epam.popcornapp.R;
import com.epam.popcornapp.glide.GlideApp;
import com.epam.popcornapp.ui.episode.EpisodeItem;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.Holder> {

    private final List<EpisodeItem> episodes;
    private final Context context;

    private ClickListener listener;

    EpisodesAdapter(final List<EpisodeItem> episodes, final Context context) {
        this.episodes = episodes;
        this.context = context;
    }

    public void setListener(final ClickListener listener) {
        this.listener = listener;
    }

    @Override
    public Holder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_episode, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        final EpisodeItem item = episodes.get(position);

        GlideApp.with(context)
                .load(item.getPosterPath())
                .defaultOptions()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.posterView);

        holder.titleView.setText(String.format(Locale.getDefault(), "%d. %s", item.getEpisodeNumber(), item.getTitle()));
        holder.ratingBar.setRating(item.getRating() / 2);
        holder.releaseDateView.setText(item.getReleaseDate());
        holder.overviewView.setText(item.getOverview());
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    public void setData(final List<EpisodeItem> items) {
        this.episodes.clear();

        if (items != null) {
            this.episodes.addAll(items);
        }

        notifyDataSetChanged();
    }

    private void onItemClick(final int position) {
        if (listener != null) {
            listener.onItemClick(episodes.get(position).getEpisodeNumber(), episodes);
        }
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.photo_image_view)
        AppCompatImageView posterView;

        @BindView(R.id.title_text_view)
        TextView titleView;

        @BindView(R.id.rating_bar)
        MaterialRatingBar ratingBar;

        @BindView(R.id.release_date_text_view)
        TextView releaseDateView;

        @BindView(R.id.overview_text_view)
        TextView overviewView;

        Holder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            onItemClick(getAdapterPosition());
        }

    }

    interface ClickListener {
        void onItemClick(int number, List<EpisodeItem> episodeItemList);
    }
}
