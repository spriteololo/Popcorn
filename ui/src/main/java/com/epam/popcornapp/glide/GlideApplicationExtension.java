package com.epam.popcornapp.glide;

import com.bumptech.glide.Priority;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.epam.ui.R;

@GlideExtension
public class GlideApplicationExtension {

    private GlideApplicationExtension() {
    }

    @GlideOption
    public static void defaultOptions(final RequestOptions options) {
        options
                .placeholder(R.color.color_card_background)
                .fallback(R.color.color_card_background)
                .error(R.color.color_card_background)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .priority(Priority.HIGH);
    }
}
