package io.github.dmitrikudrenko.mdrxl.sample.ui.women.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.SampleApplication;
import io.github.dmitrikudrenko.mdrxl.sample.ui.base.BaseFragmentHolderRxActivity;
import io.github.dmitrikudrenko.mdrxl.sample.utils.ui.images.ImageLoader;

import javax.inject.Inject;

public class GeraltWomanActivity extends BaseFragmentHolderRxActivity<GeraltWomanFragment> {
    public static Intent intent(final Context context) {
        return new Intent(context, GeraltWomanActivity.class);
    }

    @Inject
    ImageLoader imageLoader;

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.toolbar_image)
    ImageView toolbarImageView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected GeraltWomanFragment createFragment() {
        return GeraltWomanFragment.create();
    }

    @Override
    protected void beforeOnCreate(final Bundle savedInstanceState) {
        SampleApplication.get().inject(this);
    }

    @Override
    protected int contentView() {
        return R.layout.a_fragment_holder_collapsing;
    }

    void loadPhotoIntoToolbar(final String url) {
        imageLoader.loadFitCroppedImageInto(url, toolbarImageView);
    }

    public void showTitle(final String value) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(value);
        }
        collapsingToolbarLayout.setTitle(value);
    }

    @OnClick(R.id.toolbar_image)
    void onPhotoClicked() {
        getFragment().onPhotoClicked();
    }
}
