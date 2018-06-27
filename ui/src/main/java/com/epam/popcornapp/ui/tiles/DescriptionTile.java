package com.epam.popcornapp.ui.tiles;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.epam.ui.R;

public class DescriptionTile extends BaseTile {

    private TextView nameView;
    private TextView descriptionView;

    public DescriptionTile(final Context context) {
        super(context);
    }

    public DescriptionTile(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public DescriptionTile(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreateView(final Context pContext, final AttributeSet pAttrs) {
        super.onCreateView(pContext, pAttrs);
        nameView = findViewById(R.id.description_tile_name_text_view);
        descriptionView = findViewById(R.id.description_tile_description_text_view);
    }

    @Override
    protected int getViewLayout() {
        return R.layout.view_tile_description;
    }

    public void setName(final String name) {
        this.nameView.setText(name);
    }

    public void setDescription(final String description) {
        this.descriptionView.setText(description);
    }
}
