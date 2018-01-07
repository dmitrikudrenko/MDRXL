package io.github.dmitrikudrenko.mdrxl.mvp;

import android.support.v4.app.LoaderManager;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class RxLoaderPresenterTest {
    private RxLoaderPresenter presenter;
    private RxLoaderManager rxLoaderManager;

    @Before
    public void setUp() {
        rxLoaderManager = new RxLoaderManager(mock(LoaderManager.class));
        presenter = new RxLoaderPresenter(rxLoaderManager);
    }

    @Test
    public void shouldReturnTheSameLoaderManager() {
        assertEquals(rxLoaderManager, presenter.getLoaderManager());
    }
}
