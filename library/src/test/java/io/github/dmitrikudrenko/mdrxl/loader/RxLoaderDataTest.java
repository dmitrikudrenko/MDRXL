package io.github.dmitrikudrenko.mdrxl.loader;

import org.junit.Test;

import static junit.framework.Assert.*;

public class RxLoaderDataTest {
    private final Object data = new Object();
    private final Throwable error = new Throwable();

    @Test
    public void shouldBeResultData() {
        final RxLoaderData<Object> result = RxLoaderData.result(data);

        assertTrue(result.isSuccess());
        assertEquals(data, result.getData());
        assertNull(result.getError());
    }

    @Test
    public void shouldBeErrorData() {
        final RxLoaderData<Object> result = RxLoaderData.error(error);

        assertFalse(result.isSuccess());
        assertNull(result.getData());
        assertEquals(error, result.getError());
    }
}
