package io.github.dmitrikudrenko.utils.common;

import javax.annotation.Nullable;

public final class Strings {
    private Strings() {
    }

    public static boolean isBlank(@Nullable final String string) {
        if (string == null) {
            return true;
        }
        final char[] chars = string.toCharArray();
        for (final char c : chars) {
            if (c != ' ') {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(@Nullable final String string) {
        return !isBlank(string);
    }

    @Nullable
    public static String nullIfBlank(@Nullable final String string) {
        return isNotBlank(string) ? string : null;
    }
}
