package com.epam.ui_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.epam.popcornapp.glide.GlideApp;
import com.epam.popcornapp.ui.credits.ActorCreditsLayout;

public class RatingTileDemoActivity extends AppCompatActivity {

    ActorCreditsLayout actors;

    String imagePath = "https://www.smashingmagazine.com/wp-content/uploads/2015/06/10-dithering-opt.jpg";
    String imagePath2 = "https://image.tmdb.org/t/p/w342/q0R4crx2SehcEEQEkYObktdeFy.jpg";
    String imagePath3 = "https://cdn.arstechnica.net/wp-content/uploads/2016/02/5718897981_10faa45ac3_b-640x624.jpg";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiles_demo);
        final ImageView avatar = findViewById(R.id.imgBig);

        GlideApp.with(this)
                .load("https://www.gravatar.com/avatar/615953b7bd7d93f19170f978774a7609?s=1000")
                .defaultOptions()
                .transition(DrawableTransitionOptions.withCrossFade(650))
                .into(avatar);
       // final boolean c = getIntent().getBooleanExtra("bool", false);

        /*final Bundle b = getIntent().getExtras();

        final boolean a = b.getBoolean("LOGIN_BOOL");*/

    }



}
