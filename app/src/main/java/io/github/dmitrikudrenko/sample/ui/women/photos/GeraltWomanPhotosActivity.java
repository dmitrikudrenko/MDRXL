package io.github.dmitrikudrenko.sample.ui.women.photos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import io.github.dmitrikudrenko.sample.R;
import io.github.dmitrikudrenko.sample.ui.base.BaseFragmentHolderRxActivity;

public class GeraltWomanPhotosActivity extends BaseFragmentHolderRxActivity<GeraltWomanPhotosFragment> {
    public static Intent intent(final Context context) {
        return new Intent(context, GeraltWomanPhotosActivity.class);
    }

    @Override
    protected GeraltWomanPhotosFragment createFragment() {
        return GeraltWomanPhotosFragment.create();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void showPages(final String value) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(value);
        }
    }

    @Override
    protected int contentView() {
        return R.layout.a_photos;
    }
}
