package io.github.dmitrikudrenko.mdrxl.mvp;

import com.arellomobile.mvp.MvpPresenter;
import rx.Completable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public abstract class RxPresenter<View extends RxView> extends MvpPresenter<View> {
    private final CompositeSubscription subscription = new CompositeSubscription();

    protected void runOnUiThread(final Runnable runnable) {
        subscription.add(
                Completable.fromAction(runnable::run)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe()
        );
    }

    public void add(final Subscription subscription) {
        this.subscription.add(subscription);
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
        super.onDestroy();
    }
}
