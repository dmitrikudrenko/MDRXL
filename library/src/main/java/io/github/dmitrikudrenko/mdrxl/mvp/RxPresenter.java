package io.github.dmitrikudrenko.mdrxl.mvp;

import com.arellomobile.mvp.MvpPresenter;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import rx.Completable;
import rx.android.schedulers.AndroidSchedulers;

public abstract class RxPresenter<View extends RxView> extends MvpPresenter<View> {
    private final RxLoaderManager loaderManager;

    public RxPresenter(final RxLoaderManager loaderManager) {
        this.loaderManager = loaderManager;
    }

    protected RxLoaderManager getLoaderManager() {
        return loaderManager;
    }

    protected void runOnUiThread(final Runnable runnable) {
        Completable.fromAction(runnable::run).subscribeOn(AndroidSchedulers.mainThread()).subscribe();
    }
}
