package com.epam.popcornapp.ui.all.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epam.popcornapp.R;
import com.epam.popcornapp.ui.swimming.SkeletonTileType;
import com.epam.popcornapp.ui.swimming.SwimmingLineItem;
import com.epam.popcornapp.ui.swimming.SwimmingLineView;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;
import com.epam.popcornapp.ui.tiles.item.RatingTileItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseFragmentAdapter extends RecyclerView.Adapter<BaseFragmentAdapter.BaseViewHolder> {

    private Context context;
    private List<SwimmingLineItem> items;
    private OnClickListener clickListener;

    public interface OnClickListener {
        void onLineMoreClicked(int linePosition);

        void onLineTileClicked(int linePosition, int id, @TileType String type);
    }

    public void setOnClickListener(@NonNull final OnClickListener OnClickListener) {
        clickListener = OnClickListener;
    }

    public BaseFragmentAdapter(@NonNull final Context context, @NonNull final List<SwimmingLineItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(context)
                .inflate(R.layout.adapter_swimming_line_item, parent, false);

        return new BaseViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, final int position) {
        final SwimmingLineItem swimmingLineItem = items.get(position);
        final List<BaseTileItem> tileItems;

        if (swimmingLineItem == null || (tileItems = swimmingLineItem.getTileList()) == null) {
            return;
        }

        @SkeletonTileType final int skeletonTileType;
        final String title = swimmingLineItem.getTitle();

        skeletonTileType = tileItems.get(0) instanceof RatingTileItem ?
                SkeletonTileType.RATING : SkeletonTileType.DESCRIPTION;

        holder.swimmingLine.setData(skeletonTileType, title, tileItems);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setData(@NonNull final List<BaseTileItem> itemList, final String title, final int position) {
        final SwimmingLineItem item = new SwimmingLineItem();

        item.setTitle(title);
        item.setTileList(itemList);
        items.set(position, item);

        notifyItemChanged(position);
    }

    class BaseViewHolder extends RecyclerView.ViewHolder
            implements SwimmingLineView.OnSwimmingLineClickListener {

        @BindView(R.id.swimming_line_layout)
        SwimmingLineView swimmingLine;
        BaseFragmentAdapter parentAdapter;

        BaseViewHolder(@NonNull final View itemView, @NonNull final BaseFragmentAdapter adapter) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            parentAdapter = adapter;
            swimmingLine.createSkeletonTiles(SkeletonTileType.DESCRIPTION);
            swimmingLine.setOnSwimmingLineClickListener(this);
        }

        @Override
        public void onMoreClicked(@TileType final String type) {
            parentAdapter.clickListener.onLineMoreClicked(getAdapterPosition());
        }

        @Override
        public void onTileClicked(final int id, @TileType final String type, final View view) {
            parentAdapter.clickListener.onLineTileClicked(getAdapterPosition(), id, type);
        }
    }
}
