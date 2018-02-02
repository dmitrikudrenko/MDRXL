package io.github.dmitrikudrenko.sample.ui.base;

import android.os.Bundle;
import butterknife.Unbinder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class BaseRxFragmentTest {
    private ActivityController<BaseFragmentHolderRxActivityImpl> controller;

    private BaseFragmentHolderRxActivity<? extends BaseRxFragment> activity;
    private BaseRxFragment fragment;

    @Before
    public void setUp() {
        controller = Robolectric.buildActivity(BaseFragmentHolderRxActivityImpl.class);
        activity = controller.setup().get();
        fragment = activity.getFragment();
    }

    @Test
    public void shouldCleanBindersOnDestroy() {
        final Unbinder unbinder = mock(Unbinder.class);
        fragment.unbinder = unbinder;

        controller.pause().stop().destroy();

        verify(unbinder).unbind();
        assertNull(fragment.unbinder);
    }

    @Test
    public void shouldContainTheSameFragmentAfterRotation() {
        ((BaseRxFragmentImpl) fragment).fragmentId = 5;

        recreateActivity();

        final int oldFragmentId = ((BaseRxFragmentImpl) fragment).fragmentId;
        final int newFragmentId = ((BaseRxFragmentImpl) activity.getFragment()).fragmentId;
        assertEquals(oldFragmentId, newFragmentId);
        assertNotSame(fragment, activity.getFragment());
    }

    private void recreateActivity() {
        final Bundle bundle = new Bundle();

        // Destroy the original activity
        controller
                .saveInstanceState(bundle)
                .pause()
                .stop()
                .destroy();

        // Bring up a new activity
        controller = Robolectric.buildActivity(BaseFragmentHolderRxActivityImpl.class)
                .create(bundle)
                .start()
                .restoreInstanceState(bundle)
                .resume()
                .visible();
        activity = controller.get();
    }

    public static class BaseRxFragmentImpl extends BaseRxFragment {
        int fragmentId;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            fragmentId = savedInstanceState != null ? savedInstanceState.getInt("fragment_id") : 0;
        }

        @Override
        public void onSaveInstanceState(final Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putInt("fragment_id", fragmentId);
        }
    }

    public static class BaseFragmentHolderRxActivityImpl extends BaseFragmentHolderRxActivity<BaseRxFragmentImpl> {
        @Override
        protected BaseRxFragmentImpl createFragment() {
            return new BaseRxFragmentImpl();
        }
    }
}
