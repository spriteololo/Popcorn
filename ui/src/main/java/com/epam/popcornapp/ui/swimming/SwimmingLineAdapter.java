package com.epam.popcornapp.ui.swimming;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ViewSwitcher;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.epam.popcornapp.glide.GlideApp;
import com.epam.popcornapp.listeners.RecyclerViewListeners;
import com.epam.popcornapp.ui.tiles.DescriptionTile;
import com.epam.popcornapp.ui.tiles.GalleryLandscapeTile;
import com.epam.popcornapp.ui.tiles.GalleryPortraitTile;
import com.epam.popcornapp.ui.tiles.RatingTile;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;
import com.epam.popcornapp.ui.tiles.item.DescriptionTileItem;
import com.epam.popcornapp.ui.tiles.item.RatingTileItem;
import com.epam.ui.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class SwimmingLineAdapter extends RecyclerView.Adapter<SwimmingLineAdapter.Holder> {

    private int numberOfEmptyItems = 0;

    private final Context context;
    private final List<BaseTileItem> tileItems;

    private static final String TAG = "tag";
    private static final String TRANSITION_NAME = "transitionName";

    @SkeletonTileType
    private int tileType;

    private RecyclerViewListeners.OnItemClickListener itemClickListener;
    private final int ANIM_DURATION = 650;

    SwimmingLineAdapter(@NonNull final Context context, @NonNull final List<BaseTileItem> tileItems) {
        this.context = context;
        this.tileItems = new ArrayList<>(tileItems);
    }

    void setTiles(@SkeletonTileType final int tileType, final Collection<BaseTileItem> tiles) {
        if (tiles == null || tiles.isEmpty()) {
            return;
        }

        this.tileType = tileType;
        this.tileItems.clear();
        this.tileItems.addAll(tiles);
        notifyDataSetChanged();
    }

    void createSkeletonTiles(@SkeletonTileType final int tileType) {
        this.tileType = tileType;
        this.tileItems.clear();

        numberOfEmptyItems = 10;

        notifyDataSetChanged();
    }

    void setItemClickListener(@NonNull final RecyclerViewListeners.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public Holder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view;

        switch (viewType) {
            case SkeletonTileType.GALLERY_PORTRAIT:
                view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.adapter_item_gallery_portrait_tile, parent, false);

                return new GalleryPortraitHolder(view);
            case SkeletonTileType.GALLERY_LANDSCAPE:
                view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.adapter_item_gallery_landscape_tile, parent, false);

                return new GalleryLandscapeHolder(view);
            case SkeletonTileType.DESCRIPTION:
                view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.adapter_item_description_tile, parent, false);

                return new DescriptionHolder(view);
            case SkeletonTileType.RATING:
                view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.adapter_item_rating_tile, parent, false);

                return new RatingHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        switch (holder.getItemViewType()) {
            case SkeletonTileType.GALLERY_PORTRAIT:
                bindGalleryPortraitTile((GalleryPortraitHolder) holder, position);

                break;
            case SkeletonTileType.GALLERY_LANDSCAPE:
                bindGalleryLandscapeTile((GalleryLandscapeHolder) holder, position);

                break;
            case SkeletonTileType.DESCRIPTION:
                bindDescriptionTile((DescriptionHolder) holder, position);

                break;
            case SkeletonTileType.RATING:
                bindRatingTile((RatingHolder) holder, position);

                break;
            default:
                Log.e("Bind error", "Unknown tile");
                throw new IllegalStateException("Unknown tile type");
        }
    }

    @Override
    public int getItemCount() {
        return tileItems.isEmpty() ? numberOfEmptyItems : tileItems.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return this.tileType;
    }

    private void bindGalleryPortraitTile(@NonNull final GalleryPortraitHolder holder, final int position) {
        if ((tileItems.isEmpty())) {
            return;
        }

        final BaseTileItem tile = tileItems.get(position);

        GlideApp.with(context)
                .load(tile.getImagePath())
                .defaultOptions()
                .transition(DrawableTransitionOptions.withCrossFade(ANIM_DURATION))
                .into(holder.galleryPortraitTileView.getPhotoView());

        ViewCompat.setTransitionName(holder.galleryPortraitTileView.getPhotoView(), TRANSITION_NAME + position);
        holder.galleryPortraitTileView.getPhotoView().setTag(TAG + position);

        holder.viewSwitcher.setDisplayedChild(1);
    }

    private void bindGalleryLandscapeTile(@NonNull final GalleryLandscapeHolder holder, final int position) {
        if ((tileItems.isEmpty())) {
            return;
        }

        final BaseTileItem tile = tileItems.get(position);

        GlideApp.with(context)
                .load(tile.getImagePath())
                .defaultOptions()
                .transition(DrawableTransitionOptions.withCrossFade(ANIM_DURATION))
                .into(holder.galleryLandscapeTileView.getPhotoView());

        ViewCompat.setTransitionName(holder.galleryLandscapeTileView.getPhotoView(), TRANSITION_NAME + position);
        holder.galleryLandscapeTileView.getPhotoView().setTag(TAG + position);

        holder.viewSwitcher.setDisplayedChild(1);
    }

    private void bindDescriptionTile(@NonNull final DescriptionHolder holder, final int position) {
        if ((tileItems.isEmpty())) {
            return;
        }

        final DescriptionTileItem tile = (DescriptionTileItem) tileItems.get(position);

        GlideApp.with(context)
                .load(tile.getImagePath())
                .defaultOptions()
                .transition(DrawableTransitionOptions.withCrossFade(ANIM_DURATION))
                .into(holder.descriptionTileView.getPhotoView());

        holder.descriptionTileView.setName(tile.getName());
        holder.descriptionTileView.setDescription(tile.getDescription());

        holder.viewSwitcher.setDisplayedChild(1);
    }

    private void bindRatingTile(@NonNull final RatingHolder holder, final int position) {
        if (tileItems.isEmpty()) {
            return;
        }

        final RatingTileItem tile = (RatingTileItem) tileItems.get(position);

        GlideApp.with(context)
                .load(tile.getImagePath())
                .defaultOptions()
                .transition(DrawableTransitionOptions.withCrossFade(ANIM_DURATION))
                .into(holder.ratingTileView.getPhotoView());

        holder.ratingTileView.setName(tile.getName());
        holder.ratingTileView.setRating(tile.getRating());

        holder.viewSwitcher.setDisplayedChild(1);
    }

    BaseTileItem getItemByPosition(final int position) {
        if (tileItems.isEmpty()) {
            return null;
        }

        return tileItems.get(position);
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ViewSwitcher viewSwitcher;

        Holder(@NonNull final View itemView) {
            super(itemView);

            viewSwitcher = itemView.findViewById(R.id.adapter_item_tile_view_switcher);
            final Animation inAnim = new AlphaAnimation(0, 1);
            inAnim.setDuration(300);
            viewSwitcher.setInAnimation(inAnim);

            setIsRecyclable(false);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    private class GalleryPortraitHolder extends Holder {

        GalleryPortraitTile galleryPortraitTileView;

        GalleryPortraitHolder(@NonNull final View itemView) {
            super(itemView);

            galleryPortraitTileView = itemView.findViewById(R.id.adapter_item_gallery_portrait_tile_view);
        }
    }

    private class GalleryLandscapeHolder extends Holder {

        GalleryLandscapeTile galleryLandscapeTileView;

        GalleryLandscapeHolder(@NonNull final View itemView) {
            super(itemView);

            galleryLandscapeTileView = itemView.findViewById(R.id.adapter_item_gallery_landscape_tile_view);
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
}
