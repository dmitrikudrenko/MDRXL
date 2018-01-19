package io.github.dmitrikudrenko.common;

import io.github.dmitrikudrenko.utils.common.Objects;
import org.junit.Test;

import static junit.framework.Assert.*;

public class ObjectsTest {
    private final Object object1 = new Object();
    private final Object object2 = new Object();

    private final A a1 = new A();
    private final A a2 = new A();

    @Test
    public void common() {
        assertTrue(Objects.notEquals(object1, object2));
        assertFalse(Objects.notEquals(object1, object1));
        assertFalse(Objects.notEquals(a1, a2));
        assertFalse(Objects.notEquals(null, null));
        assertTrue(Objects.notEquals(null, a1));
        assertTrue(Objects.notEquals(a1, null));
    }

    private static class A {
        private final int id = 1;

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            final A a = (A) o;

            return id == a.id;
        }

        @Override
        public int hashCode() {
            return id;
        }
    }
}
