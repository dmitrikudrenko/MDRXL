package io.github.dmitrikudrenko.mdrxl.sample.ui.data.details;

import android.content.Context;
import android.content.Intent;
import io.github.dmitrikudrenko.mdrxl.sample.ui.base.BaseFragmentHolderRxActivity;

public class DataDetailsActivity extends BaseFragmentHolderRxActivity<DataDetailsFragment> {
    private static final String ARG_ID = "id";

    public static Intent intent(final Context context, final long id) {
        return new Intent(context, DataDetailsActivity.class)
                .putExtra(ARG_ID, id);
    }

    @Override
    protected DataDetailsFragment createFragment() {
        return DataDetailsFragment.create(getIntent().getLongExtra(ARG_ID, -1));
    }
}
