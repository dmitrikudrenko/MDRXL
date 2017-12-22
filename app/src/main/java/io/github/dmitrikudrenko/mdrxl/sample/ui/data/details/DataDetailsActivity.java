package io.github.dmitrikudrenko.mdrxl.sample.ui.data.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.ui.base.BaseFragmentHolderRxActivity;

public class DataDetailsActivity extends BaseFragmentHolderRxActivity<DataDetailsFragment> {
    private static final String ARG_ID = "id";

    public static Intent intent(final Context context, final long id) {
        return new Intent(context, DataDetailsActivity.class)
                .putExtra(ARG_ID, id);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected DataDetailsFragment createFragment() {
        return DataDetailsFragment.create(getIntent().getLongExtra(ARG_ID, -1));
    }
}
