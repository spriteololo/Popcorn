package com.epam.popcornapp.ui.lists.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ViewSwitcher;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.glide.GlideApp;
import com.epam.popcornapp.ui.tiles.DescriptionTile;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;
import com.epam.popcornapp.ui.tiles.item.DescriptionTileItem;

import java.util.List;

public class ListDetailMediaAdapter extends RecyclerView.Adapter<ListDetailMediaAdapter.Holder> {

    private static final int NUM_OF_EMPTY_ITEMS = 10;

    private final Context context;
    private final List<BaseTileItem> data;

    private ListMediaAdapterClickListener clickListener;
    private int itemNum;

    ListDetailMediaAdapter(final List<BaseTileItem> data, final Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new Holder(LayoutInflater
                .from(context)
                .inflate(com.epam.ui.R.layout.adapter_item_more_screen_desription_tile, parent, false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        if (data.isEmpty()) {
            return;
        }

        final DescriptionTileItem tileItem = (DescriptionTileItem) data.get(position);

        GlideApp.with(context)
                .load(tileItem.getImagePath())
                .defaultOptions()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.descriptionTileView.getPhotoView());

        holder.descriptionTileView.setName(tileItem.getName());
        holder.descriptionTileView.setDescription(tileItem.getDescription());

        holder.viewSwitcher.setDisplayedChild(1);
    }

    @Override
    public int getItemCount() {
        return data.isEmpty() ? Math.min(itemNum, NUM_OF_EMPTY_ITEMS) : data.size();
    }

    public void setClickListener(final ListMediaAdapterClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setItemNum(final int itemNum) {
        this.itemNum = itemNum;
    }

    void addNewItem(final BaseTileItem item) {
        if (item == null) {
            return;
        }

        data.add(0, item);
        itemNum = data.size();

        notifyItemInserted(0);
    }

    void deleteItem(final int position) {
        data.remove(position);
        itemNum = data.size();

        notifyItemRemoved(position);
    }

    public void setData(final List<BaseTileItem> result) {
        if (result == null || result.isEmpty()) {
            return;
        }

        data.clear();
        data.addAll(result);

        notifyDataSetChanged();
    }

    public interface ListMediaAdapterClickListener {

        void onItemClick(int itemId, int position, @TileType String tileType);

        void onMenuItemClick(int itemId, int position, @TileType String tileType);
    }

    class Holder extends RecyclerView.ViewHolder implements
            View.OnClickListener,
            View.OnCreateContextMenuListener {

        ViewSwitcher viewSwitcher;
        DescriptionTile descriptionTileView;

        Holder(final View itemView) {
            super(itemView);

            viewSwitcher = itemView.findViewById(com.epam.ui.R.id.adapter_item_tile_view_switcher);
            descriptionTileView = itemView.findViewById(com.epam.ui.R.id.adapter_item_description_tile_view);

            final Animation inAnim = new AlphaAnimation(0, 1);
            inAnim.setDuration(Constants.ANIM_FAST_DURATION);

            viewSwitcher.setInAnimation(inAnim);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(final View view) {
            final int position = getAdapterPosition();
            final BaseTileItem baseTileItem = data.get(position);

            clickListener.onItemClick(baseTileItem.getId(), position, baseTileItem.getType());
        }

        @Override
        public void onCreateContextMenu(final ContextMenu contextMenu, final View view,
                                        final ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu
                    .add(Menu.NONE, view.getId(), Menu.NONE, context.getString(com.epam.ui.R.string.delete))
                    .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(final MenuItem menuItem) {
                            final int position = getAdapterPosition();
                            final BaseTileItem baseTileItem = data.get(position);

                            clickListener.onMenuItemClick(baseTileItem.getId(), position, baseTileItem.getType());

                            return true;
                        }
                    });
        }
    }
}
