package com.epam.popcornapp.ui.lists.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epam.popcornapp.R;
import com.epam.popcornapp.pojo.lists.ListItem;

import java.util.ArrayList;
import java.util.List;

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.Holder> {

    private final Context context;
    private final List<ListItem> data = new ArrayList<>();

    private ClickListener clickListener;

    public ListsAdapter(final Context context) {
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new Holder(LayoutInflater
                .from(context)
                .inflate(R.layout.adapter_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        if (data.isEmpty()) {
            return;
        }

        final ListItem tileItem = data.get(position);
        final int itemCount = tileItem.getItemCount();

        holder.nameTextView.setText(tileItem.getName());
        holder.itemCountTextView.setText(context.getResources().getQuantityString(R.plurals.item_count, itemCount, itemCount));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(@NonNull final List<ListItem> result) {
        if (result.isEmpty()) {
            return;
        }

        data.clear();
        data.addAll(result);

        notifyDataSetChanged();
    }

    public void setNewList(@NonNull final ListItem result) {
        data.add(0, result);

        notifyItemInserted(0);
    }

    public void increaseItemsNum(final int position) {
        final ListItem listItem = data.get(position);

        listItem.setItemCount(listItem.getItemCount() + 1);

        notifyItemChanged(position);
    }

    public interface ClickListener {

        void onItemClick(int itemId, int position);
    }

    public void setClickListener(final ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTextView;
        TextView itemCountTextView;

        Holder(final View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.list_name_text_view);
            itemCountTextView = itemView.findViewById(R.id.list_item_count_text_view);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            final ListItem listItem = data.get(getAdapterPosition());

            clickListener.onItemClick(listItem.getId(), getAdapterPosition());
        }
    }
}
