package com.epam.popcornapp.listeners;

import android.view.View;

public class RecyclerViewListeners {

    public interface OnLoadMoreListener {
        void onLoadMore(int nextPage);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
