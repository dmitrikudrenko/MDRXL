package io.github.dmitrikudrenko.sample.ui.navigation;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import io.github.dmitrikudrenko.sample.GeraltApplication;
import io.github.dmitrikudrenko.sample.R;
import io.github.dmitrikudrenko.sample.ui.settings.SettingsFragment;
import io.github.dmitrikudrenko.sample.ui.video.list.WitcherVideosFragment;
import io.github.dmitrikudrenko.sample.ui.video.player.VideoPlayerActivity;
import io.github.dmitrikudrenko.sample.ui.video.player.VideoPlayerFragment;
import io.github.dmitrikudrenko.sample.ui.video.queue.VideoQueueActivity;
import io.github.dmitrikudrenko.sample.ui.women.details.GeraltWomanActivity;
import io.github.dmitrikudrenko.sample.ui.women.details.GeraltWomanFragment;
import io.github.dmitrikudrenko.sample.ui.women.list.GeraltWomenFragment;
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
    private AppCompatActivity activity;
    private FragmentTransaction fragmentTransaction;
    private Router router;

    @Before
    public void setUp() {
        activity = spy(Robolectric.setupActivity(AppCompatActivity.class));
    }

    @Test
    public void shouldOpenPhotos() {
        setupRouter(true);
        router.openGeraltWomanPhotos();
        assertNextStartedActivity(GeraltWomanPhotosActivity.class.getName());
    }

    @Test
    public void shouldOpenDetailsIfNotMultiWindow() {
        setupRouter(false);

        final int id = 0;
        final ClickInfo clickInfo = mock(ClickInfo.class);
        router.openGeraltWoman(id, clickInfo);

        assertNextStartedActivity(GeraltWomanActivity.class.getName());

        assertEquals(id, GeraltApplication.getWomanComponent().id());
    }

    @Test
    public void shouldOpenDetailsIfMultiWindow() {
        setupRouter(true);

        final int id = 0;
        final ClickInfo clickInfo = mock(ClickInfo.class);
        router.openGeraltWoman(id, clickInfo);

        assertEquals(id, GeraltApplication.getWomanComponent().id());

        verifyDetailsFragment(GeraltWomanFragment.class);
    }

    @Test
    public void shouldOpenVideoPlayerIfMultiWindow() {
        setupRouter(true);

        final int id = 0;
        final ClickInfo clickInfo = mock(ClickInfo.class);
        router.openGeraltVideo(id, clickInfo);

        assertEquals(id, GeraltApplication.getVideoComponent().id());

        verifyDetailsFragment(VideoPlayerFragment.class);
    }

    @Test
    public void shouldOpenVideoPlayerIfNotMultiWindow() {
        setupRouter(false);

        final int id = 0;
        final ClickInfo clickInfo = mock(ClickInfo.class);
        router.openGeraltVideo(id, clickInfo);

        assertNextStartedActivity(VideoPlayerActivity.class.getName());

        assertEquals(id, GeraltApplication.getVideoComponent().id());
    }

    @Test
    public void shouldOpenVideoQueue() {
        setupRouter(false);
        router.openVideoQueue();
        assertNextStartedActivity(VideoQueueActivity.class.getName());
    }

    @Test
    public void shouldOpenGeraltWomen() {
        setupRouter(true);
        router.openGeraltWomen();
        verifyContentFragment(GeraltWomenFragment.class);
        verifyDetailsFragmentClosed();
    }

    @Test
    public void shouldOpenWitcherVideos() {
        setupRouter(true);
        router.openWitcherVideos();
        verifyContentFragment(WitcherVideosFragment.class);
        verifyDetailsFragmentClosed();
    }

    @Test
    public void shouldOpenSettings() {
        setupRouter(true);
        router.openSettings();
        verifyContentFragment(SettingsFragment.class);
        verifyDetailsFragmentClosed();
    }

    private void verifyDetailsFragmentClosed() {
        verify(fragmentTransaction).remove(any(Fragment.class));
    }

    private <T extends Fragment> void verifyContentFragment(final Class<T> fragmentClass) {
        verify(fragmentTransaction).replace(eq(R.id.content),
                any(fragmentClass), eq("content_fragment"));
    }

    private <T extends Fragment> void verifyDetailsFragment(final Class<T> fragmentClass) {
        verify(fragmentTransaction).replace(eq(R.id.details),
                any(fragmentClass), eq("details_fragment"));
    }

    private void assertNextStartedActivity(final String componentName) {
        final Intent intent = shadowOf(activity).getNextStartedActivity();
        assertNotNull(intent);
        assertNotNull(intent.getComponent());
        assertEquals(componentName, intent.getComponent().getClassName());
    }

    private void setupRouter(final boolean multiWindow) {
        if (multiWindow) {
            fragmentTransaction = mock(FragmentTransaction.class);
            final FragmentManager fragmentManager = mock(FragmentManager.class);
            when(fragmentManager.findFragmentByTag("details_fragment")).thenReturn(mock(Fragment.class));
            when(fragmentManager.beginTransaction()).thenReturn(fragmentTransaction);
            when(fragmentTransaction.replace(anyInt(), any(Fragment.class), anyString())).thenReturn(fragmentTransaction);
            when(fragmentTransaction.remove(any(Fragment.class))).thenReturn(fragmentTransaction);
            when(activity.getSupportFragmentManager()).thenReturn(fragmentManager);
        }
        router = new Router(() -> activity, multiWindow);
    }
}
