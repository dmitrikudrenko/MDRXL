package io.github.dmitrikudrenko.sample.ui;

import io.github.dmitrikudrenko.sample.BuildConfig;
import io.github.dmitrikudrenko.sample.ui.navigation.Router;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class RootPresenterTest extends PresenterTest {
    private RootPresenter presenter;
    private RootView view;
    private RootView$$State state;

    private Router router;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        router = mock(Router.class);
        presenter = new RootPresenter(router);

        view = mock(RootView.class);
        state = mock(RootView$$State.class);

        presenter.setViewState(state);
        presenter.attachView(view);
    }

    @Test
    public void shouldOpenGeraltWomenOnAttach() {
        verify(router).openGeraltWomen();
    }

    @Test
    public void shouldOpenVideos() {
        verify(router).openGeraltWomen();

        presenter.onVideoClicked();
        verify(router).openWitcherVideos();

        presenter.onVideoClicked();
        verifyNoMoreInteractions(router);
    }

    @Test
    public void shouldOpenSettings() {
        verify(router).openGeraltWomen();

        presenter.onSettingsClicked();
        verify(router).openSettings();

        presenter.onSettingsClicked();
        verifyNoMoreInteractions(router);
    }

    @Test
    public void shouldOpenGeraltWomenIfNotSelected() {
        verify(router).openGeraltWomen();

        presenter.onWomenClicked();
        verifyNoMoreInteractions(router);

        presenter.onSettingsClicked();
        presenter.onBackPressed();

        verify(router).openGeraltWomen();
        verify(state).setWomenSectionSelected();
    }

    @Test
    public void shouldBeClosed() {
        presenter.onBackPressed();
        verify(state).close();
    }
}
