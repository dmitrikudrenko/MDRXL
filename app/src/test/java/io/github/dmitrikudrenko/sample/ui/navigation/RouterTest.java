package io.github.dmitrikudrenko.sample.ui.navigation;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import io.github.dmitrikudrenko.mdrxl.mvp.RxActivity;
import io.github.dmitrikudrenko.sample.R;
import io.github.dmitrikudrenko.sample.SampleApplication;
import io.github.dmitrikudrenko.sample.ui.women.details.GeraltWomanActivity;
import io.github.dmitrikudrenko.sample.ui.women.details.GeraltWomanFragment;
import io.github.dmitrikudrenko.sample.ui.women.photos.GeraltWomanPhotosActivity;
import io.github.dmitrikudrenko.sample.utils.ui.ClickInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class RouterTest {
    private RxActivity activity;
    private FragmentTransaction fragmentTransaction;
    private Router router;

    @Before
    public void setUp() {
        activity = spy(Robolectric.setupActivity(RxActivity.class));
    }

    @Test
    public void shouldOpenPhotos() {
        setupRouter(true);

        router.openGeraltWomanPhotos();
        final Intent intent = shadowOf(activity).getNextStartedActivity();
        assertNotNull(intent);
        assertNotNull(intent.getComponent());
        assertEquals(GeraltWomanPhotosActivity.class.getName(), intent.getComponent().getClassName());
    }

    @Test
    public void shouldOpenDetailsIfNotMultiWindow() {
        setupRouter(false);

        final int id = 0;
        final ClickInfo clickInfo = mock(ClickInfo.class);
        router.openGeraltWoman(id, clickInfo);

        final Intent intent = shadowOf(activity).getNextStartedActivity();
        assertNotNull(intent);
        assertNotNull(intent.getComponent());
        assertEquals(GeraltWomanActivity.class.getName(), intent.getComponent().getClassName());

        assertEquals(id, SampleApplication.getWomanComponent().id());
    }

    @Test
    public void shouldOpenDetailsIfMultiWindow() {
        setupRouter(true);

        final int id = 0;
        final ClickInfo clickInfo = mock(ClickInfo.class);
        router.openGeraltWoman(id, clickInfo);

        assertEquals(id, SampleApplication.getWomanComponent().id());

        verify(fragmentTransaction).replace(eq(R.id.details),
                any(GeraltWomanFragment.class), eq("details_fragment"));
    }

    private void setupRouter(final boolean multiWindow) {
        if (multiWindow) {
            fragmentTransaction = mock(FragmentTransaction.class);
            final FragmentManager fragmentManager = mock(FragmentManager.class);
            when(fragmentManager.beginTransaction()).thenReturn(fragmentTransaction);
            when(fragmentTransaction.replace(anyInt(), any(Fragment.class), anyString())).thenReturn(fragmentTransaction);
            when(activity.getSupportFragmentManager()).thenReturn(fragmentManager);
        }
        router = new Router(activity, multiWindow);
    }
}
