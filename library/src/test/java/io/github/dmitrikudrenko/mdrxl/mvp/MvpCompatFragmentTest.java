package io.github.dmitrikudrenko.mdrxl.mvp;

import android.os.Bundle;
import com.arellomobile.mvp.MvpDelegate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentControllerExt;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class MvpCompatFragmentTest {
    private SupportFragmentControllerExt<? extends MvpCompatFragment> fragmentController;
    private MvpDelegate<? extends MvpCompatFragment> mvpDelegate;
    private MvpDelegate<? extends MvpCompatFragment> spyMvpDelegate;

    private final Bundle bundle = mock(Bundle.class);

    @Before
    public void setUp() {
        fragmentController = SupportFragmentControllerExt.of(new MvpCompatFragmentImpl());
        mvpDelegate = fragmentController.get().getMvpDelegate();
        spyMvpDelegate = spy(mvpDelegate);
    }

    @Test
    public void shouldMvpDelegateExist() {
        assertNotNull(mvpDelegate);
    }

    @Test
    public void shouldMvpDelegateBeSingle() {
        assertEquals(mvpDelegate, fragmentController.get().getMvpDelegate());
    }

    @Test
    public void shouldOnCreateCalledIfCreated() {
        fragmentController.get().setMvpDelegate(spyMvpDelegate);

        fragmentController.create(bundle);
        verify(spyMvpDelegate).onCreate(null);
    }

    @Test
    public void shouldOnAttachCalledIfStarted() {
        fragmentController.get().setMvpDelegate(spyMvpDelegate);

        fragmentController.create().start();
        verify(spyMvpDelegate).onAttach();
    }

    @Test
    public void shouldOnAttachCalledIfResumed() {
        fragmentController.get().setMvpDelegate(spyMvpDelegate);

        fragmentController.create().start().resume();
        verify(spyMvpDelegate, times(2)).onAttach();
    }

    //todo
    @Test
    public void shouldOnDetachCalledIfSavedInstanceState() {
        fragmentController.get().setMvpDelegate(spyMvpDelegate);

//        fragmentController.(bundle);
//        verify(mvpDelegate).onCreate(bundle);
    }

    @Test
    public void shouldOnDetachCalledIfStopped() {
        fragmentController.get().setMvpDelegate(spyMvpDelegate);

        fragmentController.create().start().resume().pause().stop();
        verify(spyMvpDelegate).onDetach();
    }

    //todo
    @Test
    public void shouldOnDestroyViewCalledIfViewDestroyed() {
        fragmentController.get().setMvpDelegate(spyMvpDelegate);

        fragmentController.create().start().resume().pause().stop().destroy();//destroyView
        verify(spyMvpDelegate, times(2)).onDetach();
//        verify(spyMvpDelegate).onDestroyView();
    }

    //todo
    @Test
    public void shouldOnDestroyCalledIfDestroyed() {
        fragmentController.get().setMvpDelegate(spyMvpDelegate);

        fragmentController.create().start().resume().pause().stop().destroy();
//        verify(spyMvpDelegate).onDestroy();
    }

    public static class MvpCompatFragmentImpl extends MvpCompatFragment {
    }
}
