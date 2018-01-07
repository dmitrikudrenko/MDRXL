package io.github.dmitrikudrenko.mdrxl.loader;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;

public final class RxLoaderArguments {
    private final Bundle args;

    public static RxLoaderArguments create(final Bundle args) {
        return new RxLoaderArguments(args);
    }

    @VisibleForTesting
    RxLoaderArguments(final Bundle args) {
        this.args = args;
    }

    public RxLoaderArguments() {
        this(new Bundle());
    }

    Bundle getArgs() {
        return args;
    }

    //todo add put-methods for other types
    public void putInt(final String key, final int value) {
        args.putInt(key, value);
    }

    public int getInt(final String key) {
        return args.getInt(key);
    }

    public void putLong(final String key, final long value) {
        args.putLong(key, value);
    }

    public long getLong(final String key) {
        return args.getLong(key);
    }
}
