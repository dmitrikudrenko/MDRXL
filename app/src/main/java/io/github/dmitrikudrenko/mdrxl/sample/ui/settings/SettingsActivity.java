package io.github.dmitrikudrenko.mdrxl.sample.ui.settings;

import io.github.dmitrikudrenko.mdrxl.sample.ui.base.BaseFragmentHolderRxActivity;

public class SettingsActivity extends BaseFragmentHolderRxActivity<SettingsFragment> {

    @Override
    protected SettingsFragment createFragment() {
        return new SettingsFragment();
    }
}
