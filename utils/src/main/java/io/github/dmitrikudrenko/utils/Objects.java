package io.github.dmitrikudrenko.utils;

import javax.annotation.Nullable;

public final class Objects {
    private Objects() {
    }

    public static boolean equals(@Nullable final Object o1, @Nullable final Object o2) {
        if (o1 == null) {
            return o2 == null;
        } else {
            return o1.equals(o2);
        }
    }

    public static boolean notEquals(@Nullable final Object o1, @Nullable final Object o2) {
        return !equals(o1, o2);
    }
}
