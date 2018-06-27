package com.epam.popcornapp.ui.credits;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.epam.popcornapp.ui.base.InflateFrameLayout;
import com.epam.popcornapp.ui.tiles.credits.BaseCreditItem;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.ui.R;

import java.util.ArrayList;
import java.util.List;

public class ActorCreditsLayout extends InflateFrameLayout implements ActorCreditsAdapter.ClickListener {

    RecyclerView actorJobsRecyclerView;
    ActorCreditsAdapter actorCreditsAdapter;
    private ClickListener clickListener;

    public ActorCreditsLayout(final Context context) {
        super(context);
    }

    public ActorCreditsLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ActorCreditsLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setClickListener(final ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onItemClick(final int id, @TileType final String mediaType) {
        clickListener.onItemClick(id, mediaType);

    }

    public interface ClickListener {
        void onItemClick(int id, @TileType String mediaType);
    }

    @Override
    protected void onCreateView(final Context pContext, final AttributeSet pAttrs) {
        actorJobsRecyclerView = findViewById(R.id.view_actor_credits_recycler_view);
        actorJobsRecyclerView.setLayoutManager(new LinearLayoutManager(pContext));
        actorCreditsAdapter = new ActorCreditsAdapter(new ArrayList<BaseCreditItem>(), getContext());
        actorCreditsAdapter.setClickListener(this);
        actorJobsRecyclerView.setAdapter(actorCreditsAdapter);
    }

    @Override
    protected int getViewLayout() {
        return R.layout.view_actor_credit;
    }

    public void setData(final List<BaseCreditItem> credits) {
        actorCreditsAdapter.setData(credits);
    }
}
