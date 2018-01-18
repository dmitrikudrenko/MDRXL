package io.github.dmitrikudrenko.utils;

import javax.annotation.Nullable;

public final class Preconditions {
    private Preconditions() {
    }
    public static <T> T checkNotNull(@Nullable final T object) {
        if (object == null) {
            throw new NullPointerException();
        }
        return object;
    }
}
