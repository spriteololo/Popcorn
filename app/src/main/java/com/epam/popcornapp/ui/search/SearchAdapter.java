package com.epam.popcornapp.ui.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.epam.popcornapp.R;
import com.epam.popcornapp.glide.GlideApp;
import com.epam.popcornapp.listeners.RecyclerViewListeners;
import com.epam.popcornapp.ui.tiles.credits.TileType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Holder> implements
        RecyclerViewListeners.OnItemClickListener {

    private static final int CONTENT_ITEM = 1;
    private static final int BUTTON_ITEM = 2;

    private final List<SearchDetail> items;
    private final Context context;

    private ClickListener clickListener;
    private boolean buttonNeed = false;

    public interface ClickListener {

        void onItemClicked(final SearchDetail item);

        void allTypesSearchClicked();
    }

    public void setClickListener(final ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public SearchAdapter(final List<SearchDetail> items, final Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        if (viewType == CONTENT_ITEM) {
            final View view = LayoutInflater.from(context)
                    .inflate(R.layout.adapter_search_item, parent, false);

            return new ViewHolder(view);
        } else {
            final View view = LayoutInflater.from(context)
                    .inflate(R.layout.adapter_search_other_types_item, parent, false);

            return new ButtonHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        if (getItemViewType(position) == CONTENT_ITEM) {
            final ViewHolder viewHolder = (ViewHolder) holder;

            final SearchDetail item = items.get(position);

            GlideApp.with(context)
                    .load(item.getImagePath())
                    .defaultOptions()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(viewHolder.photoView);

            viewHolder.titleView.setText(item.getTitle());
            viewHolder.descriptionView.setText(item.getDescription());

            final @TileType String type = item.getType();
            switch (type) {
                case TileType.ACTOR:
                    viewHolder.typeView.setText(R.string.search_people);
                    break;
                case TileType.MOVIE:
                    viewHolder.typeView.setText(R.string.search_movie);
                    break;
                case TileType.TV:
                    viewHolder.typeView.setText(R.string.search_tv_show);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (buttonNeed) {
            return items.size() + 1;
        } else {
            return items.size();
        }
    }

    @Override
    public int getItemViewType(final int position) {
        if (position >= items.size()) {
            return BUTTON_ITEM;
        } else {
            return CONTENT_ITEM;
        }
    }

    public void setData(final List<SearchDetail> data) {
        if (data == null || data.isEmpty()) {
            return;
        }

        items.clear();
        items.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(final List<SearchDetail> result) {
        if (result == null || result.isEmpty()) {
            return;
        }

        items.addAll(result);
        notifyItemRangeChanged(items.size() - result.size(), items.size());
    }

    public void showButton() {
        this.buttonNeed = true;
    }

    public void clear() {
        items.clear();
        buttonNeed = false;
        notifyDataSetChanged();
    }

    @Override
    public void onItemClick(final View view, final int position) {
        if (clickListener != null) {
            clickListener.onItemClicked(items.get(position));
        }
    }

    private void onButtonClick() {
        buttonNeed = false;
        if (clickListener != null) {
            clickListener.allTypesSearchClicked();
        }
    }

    abstract class Holder extends RecyclerView.ViewHolder {

        Holder(final View itemView) {
            super(itemView);
        }
    }

    class ViewHolder extends Holder {

        @BindView(R.id.photo_image_view)
        ImageView photoView;

        @BindView(R.id.search_title_text_view)
        TextView titleView;

        @BindView(R.id.search_description_text_view)
        TextView descriptionView;

        @BindView(R.id.search_type_text_view)
        TextView typeView;

        ViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    class ButtonHolder extends Holder {

        @BindView(R.id.other_types_button)
        Button button;

        ButtonHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    onButtonClick();
                }
            });
        }
    }
}
