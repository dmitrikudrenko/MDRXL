package io.github.dmitrikudrenko.mdrxl.mvp;

import com.arellomobile.mvp.MvpPresenter;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;

public abstract class RxPresenter<View extends RxView> extends MvpPresenter<View> {
    private final RxLoaderManager loaderManager;

    public RxPresenter(final RxLoaderManager loaderManager) {
        this.loaderManager = loaderManager;
    }

    public RxLoaderManager getLoaderManager() {
        return loaderManager;
    }
}
