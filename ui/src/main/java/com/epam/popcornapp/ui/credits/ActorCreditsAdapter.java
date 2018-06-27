package com.epam.popcornapp.ui.credits;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epam.popcornapp.ui.tiles.credits.BaseCreditItem;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.ui.R;

import java.util.List;

public class ActorCreditsAdapter extends RecyclerView.Adapter<ActorCreditsAdapter.Holder>
        implements ActorJobsAdapter.ClickListener {

    private List<BaseCreditItem> credits;
    private Context context;
    private ClickListener clickListener;

    public interface ClickListener {

        void onItemClick(int id, @TileType String mediaType);
    }

    public ActorCreditsAdapter(final List<BaseCreditItem> credits, final Context context) {
        this.credits = credits;
        this.context = context;
    }

    public void setClickListener(final ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onItemClick(final int id, @TileType final String mediaType) {
        clickListener.onItemClick(id, mediaType);
    }

    @Override
    public Holder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_item_actor_credit, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        if (position >= credits.size()) return;


        final BaseCreditItem item = credits.get(position);
        holder.creditTitleView.setText(item.category());

        if (holder.actorJobsRecyclerView.getAdapter() == null) {
            final ActorJobsAdapter adapter = new ActorJobsAdapter(item.jobs());
            adapter.setClickListener(this);
            holder.actorJobsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            holder.actorJobsRecyclerView.setAdapter(adapter);
        } else {
            ((ActorJobsAdapter) holder.actorJobsRecyclerView.getAdapter()).setData(item.jobs());
        }
    }

    @Override
    public int getItemCount() {
        return credits.size();
    }

    public void setData(final List<BaseCreditItem> credits) {
        if (credits == null || credits.size() == 0) return;

        this.credits.clear();
        this.credits.addAll(credits);
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView creditTitleView;
        RecyclerView actorJobsRecyclerView;

        public Holder(final View itemView) {
            super(itemView);

            creditTitleView = itemView.findViewById(R.id.adapter_item_actor_credit_actor_job_text_view);
            actorJobsRecyclerView = itemView.findViewById(R.id.adapter_item_actor_credit_actor_jobs_recycler_view);
        }
    }
}
