package io.github.dmitrikudrenko.mdrxl.loader;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class RxLoadersTest {
    @Test
    public void shouldReturnNewGeneratedId() {
        assertEquals(1, RxLoaders.generateId());
        assertEquals(2, RxLoaders.generateId());
        assertEquals(3, RxLoaders.generateId());
        assertEquals(4, RxLoaders.generateId());
    }
}
