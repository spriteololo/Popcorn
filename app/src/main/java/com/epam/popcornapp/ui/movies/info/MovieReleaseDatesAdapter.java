package com.epam.popcornapp.ui.movies.info;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.glide.GlideApp;
import com.epam.popcornapp.pojo.movies.release_dates.opt.ConvertedReleaseDate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class MovieReleaseDatesAdapter extends RecyclerView.Adapter<MovieReleaseDatesAdapter.MoviesReleaseDatesHolder> {

    private final List<ConvertedReleaseDate> convertedReleaseDate;

    MovieReleaseDatesAdapter(final List<ConvertedReleaseDate> convertedReleaseDate) {
        this.convertedReleaseDate = convertedReleaseDate;
    }

    void setData(final List<ConvertedReleaseDate> convertedReleaseDate) {
        if (convertedReleaseDate == null || convertedReleaseDate.isEmpty()) {
            return;
        }

        this.convertedReleaseDate.clear();
        this.convertedReleaseDate.addAll(convertedReleaseDate);
        notifyDataSetChanged();
    }

    @Override
    public MoviesReleaseDatesHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_release_date_item, parent, false);

        return new MoviesReleaseDatesHolder(view);
    }

    @Override
    public void onBindViewHolder(final MoviesReleaseDatesHolder holder, final int position) {
        GlideApp.with(holder.flagView.getContext())
                .load("http://www.geonames.org/flags/x/" +
                        convertedReleaseDate.get(position).getImagePath().toLowerCase() + ".gif")
                .defaultOptions()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.flagView);

        final int realType = convertedReleaseDate.get(position).getType();
        final String type = holder.typeView.getContext()
                .getString(Constants.MovieDetails.TYPES[realType - 1]);

        holder.typeView.setText(type);
        holder.releaseDateView.setText(convertedReleaseDate.get(position).getDate());
        holder.constraintView.setText(convertedReleaseDate.get(position).getConstraint());
    }

    @Override
    public int getItemCount() {
        return convertedReleaseDate != null ? convertedReleaseDate.size() : 0;
    }

    class MoviesReleaseDatesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.adapter_release_date_flag_image_view)
        ImageView flagView;

        @BindView(R.id.adapter_release_date_type_text_view)
        TextView typeView;

        @BindView(R.id.adapter_release_date_date_text_view)
        TextView releaseDateView;

        @BindView(R.id.adapter_release_date_constraint_text_view)
        TextView constraintView;

        MoviesReleaseDatesHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}