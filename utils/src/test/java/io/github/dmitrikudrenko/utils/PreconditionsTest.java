package io.github.dmitrikudrenko.utils;

import org.junit.Test;

import static junit.framework.Assert.assertSame;

public class PreconditionsTest {
    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerException() {
        Preconditions.checkNotNull(null);
    }

    @Test
    public void common() {
        final Object object = new Object();
        assertSame(object, Preconditions.checkNotNull(object));
    }
}
