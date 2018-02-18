package io.github.dmitrikudrenko.mdrxl.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.arellomobile.mvp.MvpAppCompatFragment;

public abstract class RxFragment extends MvpAppCompatFragment implements RxView {
    public static final String TAG_RETAINED = "retained_fragment";
    private RetainedFragment retainedFragment;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        beforeOnCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        final FragmentManager fm = getChildFragmentManager();
        retainedFragment = (RetainedFragment) fm.findFragmentByTag(TAG_RETAINED);
        if (retainedFragment == null) {
            retainedFragment = new RetainedFragment();
            fm.beginTransaction().add(retainedFragment, TAG_RETAINED).commitNow();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        retainedFragment.set(onRetainCustomNonConfigurationInstance());
    }

    protected void beforeOnCreate(final Bundle savedInstanceState) {
        //to override
    }

    @Nullable
    protected Object getLastCustomNonConfigurationInstance() {
        return retainedFragment != null ? retainedFragment.get() : null;
    }

    @Nullable
    protected Object onRetainCustomNonConfigurationInstance() {
        return null;
    }

    public static class RetainedFragment extends Fragment {
        Object data;

        @Override
        public void onCreate(@Nullable final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        private void set(final Object data) {
            this.data = data;
        }

        private Object get() {
            return data;
        }
    }
}
