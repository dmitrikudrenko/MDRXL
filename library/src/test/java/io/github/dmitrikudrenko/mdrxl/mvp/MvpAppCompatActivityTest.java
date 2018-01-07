package io.github.dmitrikudrenko.mdrxl.mvp;

import android.os.Bundle;
import com.arellomobile.mvp.MvpDelegate;
import io.github.dmitrikudrenko.mdrxl.BuildConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MvpAppCompatActivityTest {
    private ActivityController<? extends MvpAppCompatActivity> activityController;
    private MvpDelegate<? extends MvpAppCompatActivity> spyMvpDelegate;
    private MvpDelegate<? extends MvpAppCompatActivity> mvpDelegate;

    private final Bundle bundle = mock(Bundle.class);

    @Before
    public void setUp() {
        activityController = Robolectric.buildActivity(MvpAppCompatActivityImpl.class);
        mvpDelegate = activityController.get().getMvpDelegate();
        spyMvpDelegate = spy(mvpDelegate);
    }

    @Test
    public void shouldMvpDelegateExist() {
        assertNotNull(mvpDelegate);
    }

    @Test
    public void shouldMvpDelegateBeSingle() {
        assertEquals(mvpDelegate, activityController.get().getMvpDelegate());
    }

    @Test
    public void shouldOnCreateCalledIfCreated() {
        activityController.get().setMvpDelegate(spyMvpDelegate);

        activityController.create(bundle);
        verify(spyMvpDelegate).onCreate(bundle);
    }

    @Test
    public void shouldOnAttachCalledIfStarted() {
        activityController.get().setMvpDelegate(spyMvpDelegate);

        activityController.create().start();
        verify(spyMvpDelegate).onAttach();
    }

    @Test
    public void shouldOnAttachCalledIfResumed() {
        activityController.get().setMvpDelegate(spyMvpDelegate);

        activityController.create().start().resume();
        verify(spyMvpDelegate, times(2)).onAttach();
    }

    @Test
    public void shouldOnDetachCalledIfSavedInstanceState() {
        activityController.get().setMvpDelegate(spyMvpDelegate);

        activityController.create().start().resume().saveInstanceState(bundle);
        verify(spyMvpDelegate).onSaveInstanceState(bundle);
        verify(spyMvpDelegate).onDetach();
    }

    @Test
    public void shouldOnDetachCalledIfStopped() {
        activityController.get().setMvpDelegate(spyMvpDelegate);

        activityController.setup().saveInstanceState(new Bundle()).pause().stop();
        verify(spyMvpDelegate, times(2)).onDetach();
    }

    @Test
    public void shouldOnDestroyViewCalledIfDestroyed() {
        activityController.get().setMvpDelegate(spyMvpDelegate);

        activityController.setup().pause().stop().destroy();
        verify(spyMvpDelegate).onDestroyView();
    }

    @Test
    public void shouldOnDestroyCalledIfFinishingWhenDestroyed() {
        activityController.get().setMvpDelegate(spyMvpDelegate);

        final ActivityController<? extends MvpAppCompatActivity> activityController =
                this.activityController.setup().pause().stop();
        activityController.get().finish();
        activityController.destroy();

        verify(spyMvpDelegate).onDestroyView();
        verify(spyMvpDelegate).onDestroy();
    }

    private static class MvpAppCompatActivityImpl extends MvpAppCompatActivity {
    }
}
