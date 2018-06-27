package com.epam.popcornapp.ui.gallery;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.glide.GlideApp;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

public class GalleryPagerAdapter extends PagerAdapter {

    private final List<String> photos;
    private final LayoutInflater inflater;
    private Listener listener;

    GalleryPagerAdapter(final List<String> photos, final Context context) {
        this.photos = photos;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setListener(final Listener listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view =
                inflater.inflate(R.layout.adapter_gallery_item, container, false);
        final PhotoView image = view.findViewById(R.id.photo_view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            image.setTransitionName(Constants.Extras.TRANSITION_NAME + position);
            image.setTag(Constants.Extras.TAG + position);
        }

        GlideApp.with(container.getContext())
                .load(photos.get(position))
                .defaultOptions()
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(final Drawable resource, final Transition<? super Drawable> transition) {
                        image.setImageDrawable(resource);

                        if (listener != null) {
                            listener.start();
                        }
                    }
                });

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }

    interface Listener {
        void start();
    }
}
