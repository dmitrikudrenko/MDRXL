package io.github.dmitrikudrenko.mdrxl.mvp;

import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;

public class RxLoaderPresenter<View extends RxView> extends RxPresenter<View> {
    private final RxLoaderManager loaderManager;

    public RxLoaderPresenter(final RxLoaderManager loaderManager) {
        this.loaderManager = loaderManager;
    }

    protected RxLoaderManager getLoaderManager() {
        return loaderManager;
    }
}
