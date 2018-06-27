package com.epam.popcornapp.ui.all.base;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(final int layoutResID) {
        super.setContentView(layoutResID);

        onViewCreated();
    }

    @Override
    public void setContentView(final View view) {
        super.setContentView(view);

        onViewCreated();
    }

    @Override
    public void setContentView(final View view, final ViewGroup.LayoutParams params) {
        super.setContentView(view, params);

        onViewCreated();
    }

    protected void onViewCreated() {
        ButterKnife.bind(this);
        onViewsBinded();
    }

    protected void setToolbar(@NonNull final Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

    protected void setToolbar(@NonNull final Toolbar toolbar, @NonNull final CharSequence title, final boolean isShowHomeButton) {
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(isShowHomeButton);
        }
    }

    protected void setToolbarNoTitle(@NonNull final Toolbar toolbar, final boolean isShowHomeButton) {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(isShowHomeButton);
        }
    }

    protected void setToolbarTitle(@NonNull final CharSequence title) {
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    protected abstract void onViewsBinded();
}
