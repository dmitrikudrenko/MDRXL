package io.github.dmitrikudrenko.mdrxl.mvp;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class PresenterExtensionTest {
    private RxPresenter<RxView> presenter;
    private PresenterExtension extension;

    @Before
    public void setUp() {
        presenter = new RxPresenterImpl();
        extension = spy(new PresenterExtensionImpl());
        presenter.add(extension);
    }

    @Test
    public void shouldOnFirstViewAttachBeCalled() {
        presenter.onFirstViewAttach();
        verify(extension).onFirstAttach();
    }

    @Test
    public void shouldOnAttachViewBeCalled() {
        presenter.attachView(mock(RxView.class));
        verify(extension).onAttachView();
    }

    @Test
    public void shouldOnDetachViewBeCalled() {
        presenter.detachView(mock(RxView.class));
        verify(extension).onDetachView();
    }

    @Test
    public void shouldOnDestroyViewBeCalled() {
        presenter.destroyView(mock(RxView.class));
        verify(extension).onDestroyView();
    }

    @Test
    public void shouldOnDestroyBeCalled() {
        presenter.onDestroy();
        verify(extension).onDestroy();
    }

    private static class RxPresenterImpl extends RxPresenter<RxView> {
    }

    private static class PresenterExtensionImpl extends PresenterExtension {
    }
}
