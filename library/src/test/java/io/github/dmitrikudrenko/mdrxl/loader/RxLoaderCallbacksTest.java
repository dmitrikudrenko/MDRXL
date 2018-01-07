package io.github.dmitrikudrenko.mdrxl.loader;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RxLoaderCallbacksTest {
    private static final int LOADER_ID = 1;
    private RxLoaderCallbacks<Object> loaderCallbacks;
    private RxLoader mockLoader;

    @Before
    public void setUp() {
        mockLoader = mock(RxLoader.class);
        when(mockLoader.getId()).thenReturn(LOADER_ID);
        loaderCallbacks = spy(new RxLoaderCallbacksImpl());
    }

    @Test
    public void shouldGetLoaderOnCreate() {
        assertEquals(mockLoader, loaderCallbacks.onCreateLoader(LOADER_ID, null));
    }

    @Test
    public void shouldCallSuccess() {
        final Object data = new Object();
        final RxLoaderData<Object> result = RxLoaderData.result(data);
        loaderCallbacks.onLoadFinished(mockLoader, result);
        verify(loaderCallbacks).onSuccess(LOADER_ID, data);
    }

    @Test
    public void shouldCallError() {
        final Throwable error = new Throwable();
        final RxLoaderData<Object> result = RxLoaderData.error(error);
        loaderCallbacks.onLoadFinished(mockLoader, result);
        verify(loaderCallbacks).onError(LOADER_ID, error);
    }

    @Test
    public void shouldReset() {
        loaderCallbacks.onLoaderReset(mockLoader);
    }

    private class RxLoaderCallbacksImpl extends RxLoaderCallbacks<Object> {

        @Override
        protected RxLoader<Object> getLoader(final int id, final RxLoaderArguments args) {
            return mockLoader;
        }

        @Override
        protected void onSuccess(final int id, final Object data) {
        }

        @Override
        protected void onError(final int id, final Throwable error) {
        }
    }
}
