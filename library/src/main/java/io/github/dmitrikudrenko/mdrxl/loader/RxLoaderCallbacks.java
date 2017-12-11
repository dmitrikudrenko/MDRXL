package io.github.dmitrikudrenko.mdrxl.loader;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

public abstract class RxLoaderCallbacks<D> implements LoaderManager.LoaderCallbacks<RxLoaderData<D>> {
    @Override
    public Loader<RxLoaderData<D>> onCreateLoader(final int id, final Bundle args) {
        Log.d(getClass().getSimpleName(), "onCreateLoader #" + id);
        return getLoader(id);
    }

    @Override
    public void onLoadFinished(final Loader<RxLoaderData<D>> loader, final RxLoaderData<D> result) {
        Log.d(getClass().getSimpleName(), "onLoadFinished #" + loader.getId()
                + ", result success = " + result.isSuccess());
        if (result.isSuccess()) {
            onSuccess(loader.getId(), result.getData());
        } else {
            onError(loader.getId(), result.getError());
        }
    }

    @Override
    public void onLoaderReset(final Loader<RxLoaderData<D>> loader) {
        Log.d(getClass().getSimpleName(), "onLoaderReset #" + loader.getId());
    }

    protected abstract RxLoader<D> getLoader(int id);

    protected abstract void onSuccess(int id, D data);

    protected abstract void onError(int id, Throwable error);
}
