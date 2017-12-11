package io.github.dmitrikudrenko.mdrxl.loader;

import android.os.Bundle;

public final class RxLoaderArguments {
    private final Bundle args;

    public static RxLoaderArguments create(final Bundle args) {
        return new RxLoaderArguments(args);
    }

    private RxLoaderArguments(final Bundle args) {
        this.args = args;
    }

    Bundle getArgs() {
        return args;
    }
}
