package io.github.dmitrikudrenko.mdrxl.loader;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;

import javax.annotation.Nullable;

public final class RxLoaderManager {
    private final LoaderManager loaderManager;

    public RxLoaderManager(final LoaderManager loaderManager) {
        this.loaderManager = loaderManager;
    }

    public <D> void init(final int id, @Nullable final RxLoaderArguments args, final RxLoaderCallbacks<D> callbacks) {
        final Bundle arguments = args != null ? args.getArgs() : null;
        loaderManager.initLoader(id, arguments, callbacks);
    }
}
