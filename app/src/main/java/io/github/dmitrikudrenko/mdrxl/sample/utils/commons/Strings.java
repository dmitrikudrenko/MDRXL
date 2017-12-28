package io.github.dmitrikudrenko.mdrxl.sample.utils.commons;

import javax.annotation.Nullable;

public class Strings {
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
}
