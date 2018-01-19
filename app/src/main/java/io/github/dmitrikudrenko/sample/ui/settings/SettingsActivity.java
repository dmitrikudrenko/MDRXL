package io.github.dmitrikudrenko.sample.ui.settings;

import android.os.Bundle;
import io.github.dmitrikudrenko.sample.ui.base.BaseFragmentHolderRxActivity;

public class SettingsActivity extends BaseFragmentHolderRxActivity<SettingsFragment> {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected SettingsFragment createFragment() {
        return new SettingsFragment();
    }
}
