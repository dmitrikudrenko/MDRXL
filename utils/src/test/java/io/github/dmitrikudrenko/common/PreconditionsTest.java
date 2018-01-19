package io.github.dmitrikudrenko.common;

import io.github.dmitrikudrenko.utils.common.Preconditions;
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
