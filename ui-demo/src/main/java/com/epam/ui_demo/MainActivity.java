package com.epam.ui_demo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.epam.popcornapp.glide.GlideApp;
import com.epam.ui_demo.navigation_and_blur.NavigationViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.navigation_view_demo_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(MainActivity.this, NavigationViewActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.rating_tile_demo_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(MainActivity.this, RatingTileDemoActivity.class);
                startActivity(intent);
            }
        });
        final TextView textView = findViewById(R.id.textVV);
        final ImageView imgView = findViewById(R.id.img);

        GlideApp
                .with(this)
                .asBitmap()
                .load("https://www.gravatar.com/avatar/615953b7bd7d93f19170f978774a7609?s=50")
                .defaultOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(final Bitmap resource,
                                                final Transition<? super Bitmap> transition) {
                        final RoundedBitmapDrawable rbd = RoundedBitmapDrawableFactory.create(getResources(), resource);
                        rbd.setCircular(true);
                        imgView.setImageDrawable(rbd);
                    }
                });

        imgView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(final View v) {

                final Intent i = new Intent(MainActivity.this, RatingTileDemoActivity.class);

               /* final ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, imgView, getString(R.string.avatarTransition));

                final Bundle bundle = transitionActivityOptions.toBundle();*/
                i.putExtra("bool", true);

                final Pair<View, String> p1 = Pair.create((View) textView, "transitionText");
                final Pair<View, String> p2 = Pair.create((View) imgView, "transitionName");
                final ActivityOptions options = ActivityOptions.
                        makeSceneTransitionAnimation(MainActivity.this, p1, p2);
                startActivity(i, options.toBundle());
            }
        });


    }
}