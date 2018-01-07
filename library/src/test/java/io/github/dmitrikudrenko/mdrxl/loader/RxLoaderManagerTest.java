package io.github.dmitrikudrenko.mdrxl.loader;

import android.support.v4.app.LoaderManager;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class RxLoaderManagerTest {
    private LoaderManager loaderManager;
    private RxLoaderManager rxLoaderManager;

    @Before
    public void setUp() {
        loaderManager = mock(LoaderManager.class);
        rxLoaderManager = new RxLoaderManager(loaderManager);
    }

    @Test
    public void shouldReturnLoaderById() {
        final int id = 1;

        rxLoaderManager.getLoader(id);
        verify(loaderManager).getLoader(id);
    }

    @Test
    public void shouldInitLoaderWithArguments() {
        final int id = 1;
        final RxLoaderArguments arguments = new RxLoaderArguments();
        final RxLoaderCallbacks loaderCallbacks = mock(RxLoaderCallbacks.class);

        rxLoaderManager.init(id, arguments, loaderCallbacks);
        verify(loaderManager).initLoader(id, arguments.getArgs(), loaderCallbacks);
    }

    @Test
    public void shouldInitLoaderWithoutArguments() {
        final int id = 1;
        final RxLoaderCallbacks loaderCallbacks = mock(RxLoaderCallbacks.class);

        rxLoaderManager.init(id, null, loaderCallbacks);
        verify(loaderManager).initLoader(id, null, loaderCallbacks);
    }
}
