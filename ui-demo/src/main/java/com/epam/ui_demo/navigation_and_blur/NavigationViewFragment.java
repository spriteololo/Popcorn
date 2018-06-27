package com.epam.ui_demo.navigation_and_blur;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.epam.popcornapp.glide.GlideApp;
import com.epam.popcornapp.ui.tiles.BaseTile;
import com.epam.ui_demo.R;

public class NavigationViewFragment extends Fragment {
    BaseTile tileView;

    public static NavigationViewFragment getInstance() {
        return new NavigationViewFragment();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_navigation_view, container, false);

        final TextView textView = view.findViewById(R.id.fragment_navigation_view_text_view);
        final String title = getArguments().getString("data");
        textView.setText(title);

        tileView = view.findViewById(R.id.ratingTile);

        GlideApp
                .with(getActivity())
                .load("https://media.wired.com/photos/59266931f3e2356fd800928f/master/w_1432,c_limit/BNSF_Yard-copy.jpg")
                .defaultOptions()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(tileView.getPhotoView());

        return view;
    }
}
