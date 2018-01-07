package io.github.dmitrikudrenko.mdrxl.mvp;

import android.content.res.Configuration;
import android.support.annotation.Nullable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentControllerExt;

import static io.github.dmitrikudrenko.mdrxl.TestUtils.changeOrientation;
import static junit.framework.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class RxFragmentTest {
    private static final Object object = new Object();

    private SupportFragmentControllerExt<? extends RxFragment> fragmentController;
    private SupportFragmentControllerExt<? extends RxFragment> fragmentController2;

    @Before
    public void setUp() {
        fragmentController = SupportFragmentControllerExt.of(new RxFragmentImpl());
        fragmentController2 = SupportFragmentControllerExt.of(new RxFragmentImpl2());
    }

    @Test
    public void shouldSaveNonConfigurationInstance() {
        shouldSaveNonConfigurationInstance(fragmentController, object);
    }

    @Test
    public void shouldSaveNonConfigurationInstance2() {
        shouldSaveNonConfigurationInstance(fragmentController2, null);
    }

    private void shouldSaveNonConfigurationInstance(
            final SupportFragmentControllerExt<? extends RxFragment> fragmentController,
            final Object expectedNonConfigurationInstance) {
        fragmentController.create();
        assertNull(fragmentController.get().getLastCustomNonConfigurationInstance());

        final Configuration configuration = fragmentController.get().getResources().getConfiguration();
        final Configuration newConfiguration = changeOrientation(configuration);
        fragmentController.configurationChange(newConfiguration);

        assertEquals(expectedNonConfigurationInstance,
                fragmentController.get().getLastCustomNonConfigurationInstance());
    }



    public static class RxFragmentImpl extends RxFragment {
        @Nullable
        @Override
        protected Object onRetainCustomNonConfigurationInstance() {
            return object;
        }
    }

    public static class RxFragmentImpl2 extends RxFragment {
    }
}
