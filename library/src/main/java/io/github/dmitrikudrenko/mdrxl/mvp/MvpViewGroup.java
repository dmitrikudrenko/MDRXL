package io.github.dmitrikudrenko.mdrxl.mvp;

import android.view.View;

public class MvpViewGroup<P extends RxPresenter<V>, V extends RxView> {
    private P presenter;

    public MvpViewGroup(final View view) {

    }

    public void attachPresenter(final P presenter) {
        this.presenter = presenter;
        presenter.attachView((V) this);
    }

    protected P getPresenter() {
        return presenter;
    }
}
