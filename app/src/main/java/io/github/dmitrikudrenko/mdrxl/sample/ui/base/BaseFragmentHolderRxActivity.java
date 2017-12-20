package io.github.dmitrikudrenko.mdrxl.sample.ui.base;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import io.github.dmitrikudrenko.mdrxl.mvp.RxActivity;
import io.github.dmitrikudrenko.mdrxl.mvp.RxFragment;
import io.github.dmitrikudrenko.mdrxl.sample.R;

public abstract class BaseFragmentHolderRxActivity<F extends RxFragment> extends RxActivity {
    private static final String TAG_FRAGMENT = "main_fragment";

    private F fragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_fragment_holder);

        final FragmentManager fm = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragment = createFragment();
            fm.beginTransaction().replace(R.id.content, fragment, TAG_FRAGMENT).commitNow();
        } else {
            fragment = (F) fm.findFragmentByTag(TAG_FRAGMENT);
        }
    }

    protected abstract F createFragment();

    public F getFragment() {
        return fragment;
    }
}
