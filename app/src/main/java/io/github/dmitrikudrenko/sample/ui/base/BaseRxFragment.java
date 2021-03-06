package io.github.dmitrikudrenko.sample.ui.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.dmitrikudrenko.mdrxl.mvp.RxFragment;

public abstract class BaseRxFragment extends RxFragment {
    @VisibleForTesting
    @Nullable
    Unbinder unbinder;

    @Override
    @CallSuper
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }
}
