package com.epam.popcornapp.ui.gallery;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.epam.popcornapp.R;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.ui.all.base.BaseMvpActivity;

import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class GalleryActivity extends BaseMvpActivity implements GalleryPagerAdapter.Listener {

    public static Intent start(@NonNull final Context context, final GalleryParams galleryParams) {
        final Intent intent = new Intent(context, GalleryActivity.class);
        intent.putExtra(Constants.Extras.GALLERY_ITEM, galleryParams);

        return intent;
    }

    @BindView(R.id.pager)
    ViewPager pager;

    @BindView(R.id.transparent_toolbar)
    Toolbar toolbar;

    private GalleryParams galleryParams;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        supportPostponeEnterTransition();
    }

    @Override
    protected void onViewsBinded() {
        galleryParams = getIntent().getParcelableExtra(Constants.Extras.GALLERY_ITEM);

        final String title = String.format(getString(R.string.photo_index),
                galleryParams.getCurrentPhotoPosition() + 1, galleryParams.getPhotoPaths().size());

        setToolbar(toolbar, title, true);

        toolbar.setNavigationIcon(R.drawable.icon_cross_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                onBackPressed();
            }
        });

        final GalleryPagerAdapter adapter = new GalleryPagerAdapter(galleryParams.getPhotoPaths(), this);
        adapter.setListener(this);
        pager.setAdapter(adapter);
        pager.setCurrentItem(galleryParams.getCurrentPhotoPosition());
        pager.addOnPageChangeListener(new GalleryPageListener() {
            @Override
            public void pageChanged(final int position) {
                final String title = String.format(getString(R.string.photo_index),
                        position + 1, galleryParams.getPhotoPaths().size());
                setToolbarTitle(title);
            }
        });
    }

    @Override
    public void finishAfterTransition() {
        final int pos = pager.getCurrentItem();
        final Intent intent = new Intent();
        intent.putExtra(Constants.Extras.EXIT_POSITION, pos);
        setResult(RESULT_OK, intent);

        if (galleryParams.getCurrentPhotoPosition() != pos) {
            final View view = pager.findViewWithTag(Constants.Extras.TAG + pos);
            setSharedElementCallback(view);
        }

        super.finishAfterTransition();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setSharedElementCallback(final View view) {
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(final List<String> names, final Map<String, View> sharedElements) {
                names.clear();
                sharedElements.clear();
                names.add(view.getTransitionName());
                sharedElements.put(view.getTransitionName(), view);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            finish();
        } else {
            finishAfterTransition();
        }
    }

    @Override
    public void start() {
        supportStartPostponedEnterTransition();
    }
}
