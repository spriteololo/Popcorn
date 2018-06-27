package com.epam.popcornapp.ui.tiles;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.epam.popcornapp.ui.base.InflateConstraintLayout;
import com.epam.ui.R;

public class ListTile extends InflateConstraintLayout {

    private TextView nameView;
    private TextView descriptionView;

    public ListTile(final Context context) {
        super(context);
    }

    public ListTile(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ListTile(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreateView(final Context pContext, final AttributeSet pAttrs) {
        nameView = findViewById(R.id.list_tile_name_text_view);
        descriptionView = findViewById(R.id.list_tile_description_text_view);
    }

    @Override
    protected int getViewLayout() {
        return R.layout.view_tile_list;
    }

    public void setName(final String name) {
        this.nameView.setText(name);
    }

    public void setDescription(final String description) {
        this.descriptionView.setText(description);
    }
}
