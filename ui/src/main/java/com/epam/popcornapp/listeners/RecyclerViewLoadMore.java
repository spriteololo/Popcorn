package com.epam.popcornapp.listeners;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class RecyclerViewLoadMore extends RecyclerView.OnScrollListener {

    private static final int DEFAULT_ITEM_COUNT_BEFORE_LOAD_MORE = 5;

    private int currentPage = 1;
    private int prevTotalItemCount;

    private boolean isLoading = true;

    private final RecyclerViewListeners.OnLoadMoreListener onLoadMoreListener;
    private final RecyclerView.LayoutManager layoutManager;

    public RecyclerViewLoadMore(final RecyclerViewListeners.OnLoadMoreListener onLoadMoreListener,
                                final RecyclerView.LayoutManager layoutManager) {
        this.onLoadMoreListener = onLoadMoreListener;
        this.layoutManager = layoutManager;
    }

    public RecyclerViewLoadMore(final RecyclerViewListeners.OnLoadMoreListener onLoadMoreListener,
                                final RecyclerView.LayoutManager layoutManager, final int firstPage) {
        this.onLoadMoreListener = onLoadMoreListener;
        this.layoutManager = layoutManager;
        this.currentPage = firstPage;
    }

    @Override
    public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
        super.onScrolled(recyclerView, dx, dy);

        final int totalItemCount = layoutManager.getItemCount();
        final int lastVisibleItemPosition =
                ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

        if (totalItemCount < prevTotalItemCount) {
            currentPage = 1;

            isLoading = totalItemCount == 0;
        }

        if (isLoading && (totalItemCount > prevTotalItemCount)) {
            isLoading = false;
        }

        if (!isLoading &&
                (lastVisibleItemPosition + DEFAULT_ITEM_COUNT_BEFORE_LOAD_MORE) > totalItemCount) {
            currentPage++;
            onLoadMoreListener.onLoadMore(currentPage);
            isLoading = true;
        }

        prevTotalItemCount = totalItemCount;
    }

    public void clearPage() {
        currentPage = 1;
        prevTotalItemCount = 0;
    }
}