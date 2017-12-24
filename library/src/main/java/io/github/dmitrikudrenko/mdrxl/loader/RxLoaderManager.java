package io.github.dmitrikudrenko.mdrxl.loader;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import javax.annotation.Nullable;

public final class RxLoaderManager {
    private final LoaderManager loaderManager;

    public RxLoaderManager(final LoaderManager loaderManager) {
        this.loaderManager = loaderManager;
    }

    public <D> Loader<RxLoaderData<D>> init(final int id, @Nullable final RxLoaderArguments args, final RxLoaderCallbacks<D> callbacks) {
        final Bundle arguments = args != null ? args.getArgs() : null;
        return loaderManager.initLoader(id, arguments, callbacks);
    }

    @SuppressWarnings("unchecked")
    public <D> RxLoader<D> getLoader(final int id) {
        return (RxLoader) loaderManager.getLoader(id);
    }
}
