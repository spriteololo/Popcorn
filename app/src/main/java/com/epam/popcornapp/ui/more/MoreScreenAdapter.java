package com.epam.popcornapp.ui.more;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ViewSwitcher;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.glide.GlideApp;
import com.epam.popcornapp.ui.swimming.SkeletonTileType;
import com.epam.popcornapp.ui.tiles.DescriptionTile;
import com.epam.popcornapp.ui.tiles.ListTile;
import com.epam.popcornapp.ui.tiles.RatingTile;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;
import com.epam.popcornapp.ui.tiles.item.DescriptionTileItem;
import com.epam.popcornapp.ui.tiles.item.RatingTileItem;

import java.util.List;

public class MoreScreenAdapter extends RecyclerView.Adapter<MoreScreenAdapter.Holder> {

    private final Context context;
    private final List<BaseTileItem> data;
    private onClickListener clickListener;

    private static final int NUM_OF_EMPTY_ITEMS = 10;

    @SkeletonTileType
    private int tileType;

    public MoreScreenAdapter(final List<BaseTileItem> data, final Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        switch (viewType) {
            case SkeletonTileType.RATING:
                return new RatingHolder(LayoutInflater
                        .from(context)
                        .inflate(R.layout.adapter_item_more_screen_rating_tile, parent, false));
            case SkeletonTileType.LIST:
                return new ListHolder(LayoutInflater
                        .from(context)
                        .inflate(R.layout.adapter_item_more_screen_list_tile, parent, false));
            default:
                return new DescriptionHolder(LayoutInflater
                        .from(context)
                        .inflate(R.layout.adapter_item_more_screen_desription_tile, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        if (data.isEmpty()) {
            return;
        }

        switch (holder.getItemViewType()) {
            case SkeletonTileType.RATING:
                bindRatingTile((RatingHolder) holder, position);

                break;
            case SkeletonTileType.LIST:
                bindListTile((ListHolder) holder, position);

                break;
            default:
                bindDescriptionTile((DescriptionHolder) holder, position);
        }
    }

    private void bindDescriptionTile(@NonNull final DescriptionHolder holder, final int position) {
        final DescriptionTileItem tileItem = (DescriptionTileItem) data.get(position);

        holder.descriptionTileView.setName(tileItem.getName());
        holder.descriptionTileView.setDescription(tileItem.getDescription());

        GlideApp.with(context)
                .load(tileItem.getImagePath())
                .defaultOptions()
                .transition(DrawableTransitionOptions.withCrossFade(Constants.ANIM_DURATION))
                .into(holder.descriptionTileView.getPhotoView());

        holder.viewSwitcher.setDisplayedChild(1);
    }

    private void bindRatingTile(@NonNull final RatingHolder holder, final int position) {
        final RatingTileItem tileItem = (RatingTileItem) data.get(position);

        holder.ratingTileView.setName(tileItem.getName());
        holder.ratingTileView.setRating(tileItem.getRating());

        GlideApp.with(context)
                .load(tileItem.getImagePath())
                .defaultOptions()
                .transition(DrawableTransitionOptions.withCrossFade(Constants.ANIM_DURATION))
                .into(holder.ratingTileView.getPhotoView());

        holder.viewSwitcher.setDisplayedChild(1);
    }

    private void bindListTile(@NonNull final ListHolder holder, final int position) {
        final DescriptionTileItem tileItem = (DescriptionTileItem) data.get(position);

        holder.listTileView.setName(tileItem.getName());
        holder.listTileView.setDescription(tileItem.getDescription());

        holder.viewSwitcher.setDisplayedChild(1);
    }

    @Override
    public int getItemViewType(final int position) {
        return this.tileType;
    }

    @Override
    public int getItemCount() {
        return data.isEmpty() ? NUM_OF_EMPTY_ITEMS : data.size();
    }

    public void setNewItem(final BaseTileItem item) {
        if (item == null) {
            return;
        }

        data.add(0, item);

        notifyItemInserted(0);
    }

    public void deleteItem(final int position) {
        if (data.isEmpty()) {
            return;
        }

        data.remove(position);

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());
    }

    public void setData(final List<BaseTileItem> result, final boolean isRefresh,
                        @SkeletonTileType final int tileType) {
        if (result == null || result.isEmpty()) {
            return;
        }

        this.tileType = tileType;

        if (isRefresh) {
            data.clear();
        }

        data.addAll(result);

        if (data.size() < NUM_OF_EMPTY_ITEMS) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeChanged(data.size() - result.size(), result.size());
        }
    }

    public void setDescription(final int position, final String description) {
        ((DescriptionTileItem) data.get(position)).setDescription(description);

        notifyItemChanged(position);
    }

    public interface onClickListener {
        void onItemClick(int id, @TileType String mediaType, int position);
    }

    public void setClickListener(final onClickListener onClickListener) {
        clickListener = onClickListener;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ViewSwitcher viewSwitcher;

        Holder(final View itemView) {
            super(itemView);

            viewSwitcher = itemView.findViewById(R.id.adapter_item_tile_view_switcher);

            final Animation inAnim = new AlphaAnimation(0, 1);
            inAnim.setDuration(Constants.ANIM_FAST_DURATION);
            viewSwitcher.setInAnimation(inAnim);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            if (data.isEmpty()) {
                return;
            }

            final int position = getAdapterPosition();
            final BaseTileItem baseTileItem = data.get(position);

            clickListener.onItemClick(baseTileItem.getId(), baseTileItem.getType(), position);
        }
    }

    private class DescriptionHolder extends Holder {

        DescriptionTile descriptionTileView;

        DescriptionHolder(@NonNull final View itemView) {
            super(itemView);

            descriptionTileView = itemView.findViewById(R.id.adapter_item_description_tile_view);
        }
    }

    private class RatingHolder extends Holder {

        RatingTile ratingTileView;

        RatingHolder(@NonNull final View itemView) {
            super(itemView);

            ratingTileView = itemView.findViewById(R.id.adapter_item_rating_tile_view);
        }
    }

    private class ListHolder extends Holder {

        ListTile listTileView;

        ListHolder(@NonNull final View itemView) {
            super(itemView);

            listTileView = itemView.findViewById(R.id.adapter_item_list_tile_view);
        }
    }
}
