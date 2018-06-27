package com.epam.popcornapp.ui.swimming;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.epam.popcornapp.listeners.RecyclerViewListeners;
import com.epam.popcornapp.ui.base.InflateLinearLayout;
import com.epam.popcornapp.ui.base.UiConstants;
import com.epam.popcornapp.ui.tiles.BaseTile;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;
import com.epam.ui.R;

import java.util.Collection;
import java.util.Collections;

public class SwimmingLineView extends InflateLinearLayout implements
        RecyclerViewListeners.OnItemClickListener,
        View.OnClickListener {

    private TextView titleView;
    private Button moreButton;
    private RecyclerView tilesRecyclerView;

    private SwimmingLineAdapter adapter;

    private OnSwimmingLineClickListener swimmingLineClickListener;

    public SwimmingLineView(final Context context) {
        super(context);
    }

    public SwimmingLineView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public SwimmingLineView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreateView(final Context context, final AttributeSet attrs) {
        setOrientation(VERTICAL);
        titleView = findViewById(R.id.swimming_line_tile_text_view);
        moreButton = findViewById(R.id.swimming_line_more_button);
        tilesRecyclerView = findViewById(R.id.swimming_line_tiles_recycler_view);

        initTilesAdapter(context, tilesRecyclerView);

        moreButton.setOnClickListener(this);
    }

    @Override
    protected int getViewLayout() {
        return R.layout.view_swimming_line;
    }

    private void initTilesAdapter(@NonNull final Context context,
                                  @NonNull final RecyclerView tilesRecyclerView) {
        adapter = new SwimmingLineAdapter(context, Collections.EMPTY_LIST);
        adapter.setItemClickListener(this);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false);

        tilesRecyclerView.setLayoutManager(layoutManager);
        tilesRecyclerView.setAdapter(adapter);
    }

    public void setOnSwimmingLineClickListener(@NonNull final OnSwimmingLineClickListener
                                                       swimmingLineClickListener) {
        this.swimmingLineClickListener = swimmingLineClickListener;
    }

    public void createSkeletonTiles(@SkeletonTileType final int tileType) {
        setRecyclerViewHeight(tileType);

        adapter.createSkeletonTiles(tileType);
    }

    public void disableMoreButton() {
        moreButton.setVisibility(GONE);
    }

    public void setData(@SkeletonTileType final int tileType, final CharSequence title,
                        final Collection<BaseTileItem> tiles) {
        if (tiles == null || tiles.isEmpty()) {
            setVisibility(GONE);

            return;
        }

        setRecyclerViewHeight(tileType);

        titleView.setText(title);
        adapter.setTiles(tileType, tiles);
    }

    @Override
    public void onClick(final View view) {
        final BaseTileItem item = adapter.getItemByPosition(0);

        if (swimmingLineClickListener != null && item != null) {
            swimmingLineClickListener.onMoreClicked(item.getType());
        }
    }

    @Override
    public void onItemClick(final View view, final int position) {
        final BaseTileItem item = adapter.getItemByPosition(position);

        if (item == null) {
            return;
        }

        BaseTile tile = null;

        if (view instanceof ViewSwitcher && ((ViewSwitcher) view).getCurrentView() instanceof BaseTile) {
            tile = (BaseTile) ((ViewSwitcher) view).getCurrentView();
        }

        final int id = item.getId();
        @TileType final String type = item.getType();

        if (swimmingLineClickListener != null && tile != null) {
            swimmingLineClickListener.onTileClicked(id, type, tile.getPhotoView());
        }
    }

    private void setRecyclerViewHeight(@SkeletonTileType final int tileType) {
        final float newHeight;

        switch (tileType) {
            case SkeletonTileType.DESCRIPTION:
                newHeight = UiConstants.SCREEN_WIDTH * UiConstants.DESCRIPTION_LINE_PROPORTIONS;

                break;
            case SkeletonTileType.RATING:
                newHeight = UiConstants.SCREEN_WIDTH * UiConstants.RATING_LINE_PROPORTIONS;

                break;
            case SkeletonTileType.GALLERY_PORTRAIT:
                newHeight = UiConstants.SCREEN_WIDTH * UiConstants.GALLERY_PORTRAIT_LINE_PROPORTIONS;

                break;
            case SkeletonTileType.GALLERY_LANDSCAPE:
                newHeight = UiConstants.SCREEN_WIDTH * UiConstants.GALLERY_LANDSCAPE_LINE_PROPORTIONS;

                break;
            case SkeletonTileType.EMPTY:
                return;
            default:
                return;
        }

        if (newHeight != 0) {
            tilesRecyclerView.getLayoutParams().height = Math.round(newHeight);
            tilesRecyclerView.requestLayout();
        }
    }

    public View getViewByTag(final String tag) {
        return tilesRecyclerView.findViewWithTag(tag);
    }

    public interface OnSwimmingLineClickListener {

        void onMoreClicked(@TileType String type);

        void onTileClicked(int id, @TileType String type, View view);
    }
}
