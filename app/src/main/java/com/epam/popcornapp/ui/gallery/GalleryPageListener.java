package com.epam.popcornapp.ui.gallery;

import android.support.v4.view.ViewPager;

public abstract class GalleryPageListener implements ViewPager.OnPageChangeListener {

    @Override
    public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(final int position) {
        pageChanged(position);
    }

    @Override
    public void onPageScrollStateChanged(final int state) {

    }

    public abstract void pageChanged(final int position);
}
