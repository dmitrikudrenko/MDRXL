package io.github.dmitrikudrenko.mdrxl.sample.ui.settings;

import android.os.Bundle;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.ui.base.BaseFragmentHolderRxActivity;

public class SettingsActivity extends BaseFragmentHolderRxActivity<SettingsFragment> {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected SettingsFragment createFragment() {
        return new SettingsFragment();
    }
}
