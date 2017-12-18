package io.github.dmitrikudrenko.mdrxl.sample.ui.settings;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import io.github.dmitrikudrenko.mdrxl.mvp.RxActivity;
import io.github.dmitrikudrenko.mdrxl.sample.R;

public class SettingsActivity extends RxActivity {
    private static final String TAG_FRAGMENT = "settings_fragment";

    private SettingsFragment fragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_fragment_holder);

        final FragmentManager fm = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragment = new SettingsFragment();
            fm.beginTransaction().replace(R.id.content, fragment, TAG_FRAGMENT).commitNow();
        } else {
            fragment = (SettingsFragment) fm.findFragmentByTag(TAG_FRAGMENT);
        }
    }
}
