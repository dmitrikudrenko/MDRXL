package io.github.dmitrikudrenko.mdrxl.sample.utils;

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
}
