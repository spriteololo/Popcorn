package com.epam.ui_demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epam.popcornapp.ui.swimming.SkeletonTileType;
import com.epam.popcornapp.ui.swimming.SwimmingLineItem;
import com.epam.popcornapp.ui.swimming.SwimmingLineView;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;

import java.util.List;

public class TilesDemoAdapter extends RecyclerView.Adapter<TilesDemoAdapter.DemoHolder> {

    private Context context;
    private List<SwimmingLineItem> swimmingLineItems;
    private onClickListener listener;

    public interface onClickListener {
        void onLineMoreClicked(int linePosition);
        void onLineTileClicked(int linePosition, int tileItemId);
    }

    public void setListener(final onClickListener listener) {
        this.listener = listener;
    }

    public TilesDemoAdapter(final Context context, final List<SwimmingLineItem> swimmingLineItems) {
        this.context = context;
        this.swimmingLineItems = swimmingLineItems;
    }

    @Override
    public DemoHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(context)
                .inflate(R.layout.adapter_new_swimming_line, parent, false);
        return new DemoHolder(view);
    }

    @Override
    public void onBindViewHolder(final DemoHolder holder, final int position) {
        if (swimmingLineItems.get(position) == null) return;
        final List<BaseTileItem> list = swimmingLineItems.get(position).getTileList();
        final String title = swimmingLineItems.get(position).getTitle();
        @SkeletonTileType final int i = SkeletonTileType.DESCRIPTION;
        holder.layout.setData(i, title, list);
    }

    @Override
    public int getItemCount() {
        return swimmingLineItems.size();
    }

    public void setData(final List<BaseTileItem> list, final String title, final int position) {
        final SwimmingLineItem item = new SwimmingLineItem();
        item.setTitle(title);
        item.setTileList(list);
        swimmingLineItems.set(position, item);
        notifyItemChanged(position);
    }

    static class DemoHolder extends RecyclerView.ViewHolder {

        SwimmingLineView layout;

        public DemoHolder(final View itemView) {
            super(itemView);
            layout = (SwimmingLineView) itemView.findViewById(R.id.swimming_line_layout);
        }
    }
}
