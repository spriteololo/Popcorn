package com.epam.popcornapp.ui.credits;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epam.popcornapp.listeners.RecyclerViewListeners;
import com.epam.popcornapp.ui.tiles.credits.ActorJobItem;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.ui.R;

import java.util.List;

public class ActorJobsAdapter extends RecyclerView.Adapter<ActorJobsAdapter.Holder>
        implements RecyclerViewListeners.OnItemClickListener {

    private List<ActorJobItem> jobs;
    private ClickListener listener;

    public ActorJobsAdapter(final List<ActorJobItem> jobs) {
        this.jobs = jobs;
    }

    public void setClickListener(final ClickListener clickListener) {
        this.listener = clickListener;
    }

    @Override
    public void onItemClick(final View view, final int position) {
        if (listener != null) {
            final ActorJobItem item = jobs.get(position);
            listener.onItemClick(item.id(), item.type());
        }
    }

    public interface ClickListener {
        void onItemClick(int id, @TileType String mediaType);
    }

    @Override
    public Holder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_item_actor_role, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        if (position >= jobs.size()) return;

        final ActorJobItem item = jobs.get(position);

        final String role = item.role();

        holder.roleView.setText(role);
        holder.titleView.setText(item.title());
        holder.yearView.setText(item.year());

        holder.roleView.setVisibility(
                TextUtils.isEmpty(role) ? View.GONE : View.VISIBLE);

        if (position > 0) {
            holder.yearView.setVisibility(item.year().equals(jobs.get(position - 1).year()) ?
                    View.INVISIBLE : View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public void setData(final List<ActorJobItem> jobs) {
        if (jobs == null || jobs.size() == 0) return;

        this.jobs.clear();
        this.jobs.addAll(jobs);
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView yearView;
        TextView titleView;
        TextView roleView;

        public Holder(final View itemView) {
            super(itemView);

            yearView = itemView.findViewById(R.id.adapter_item_actor_role_year_text_view);
            titleView = itemView.findViewById(R.id.adapter_item_actor_role_title_text_view);
            roleView = itemView.findViewById(R.id.adapter_item_actor_role_role_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            if (listener != null) {
                onItemClick(view, getAdapterPosition());
            }
        }
    }
}
