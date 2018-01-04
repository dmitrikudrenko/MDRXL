package io.github.dmitrikudrenko.mdrxl.mvp;

import android.support.annotation.CallSuper;
import android.util.Log;
import com.arellomobile.mvp.MvpPresenter;
import rx.Completable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class RxPresenter<View extends RxView> extends MvpPresenter<View> {
    private final List<PresenterExtension> extensions = new CopyOnWriteArrayList<>();
    private final CompositeSubscription subscription = new CompositeSubscription();

    protected void runOnUiThread(final Runnable runnable) {
        subscription.add(
                Completable.fromAction(runnable::run)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> Log.d(getClass().getSimpleName(), "runOnUiThread"),
                                error -> Log.d(getClass().getSimpleName(), error.getMessage(), error))
        );
    }

    public void add(final Subscription subscription) {
        this.subscription.add(subscription);
    }

    public void add(final PresenterExtension extension) {
        extensions.add(extension);
    }

    @CallSuper
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        for (final PresenterExtension extension : extensions) {
            extension.onFirstAttach();
        }
    }

    @CallSuper
    @Override
    public void attachView(final View view) {
        super.attachView(view);
        for (final PresenterExtension extension : extensions) {
            extension.onAttachView();
        }
    }

    @CallSuper
    @Override
    public void detachView(final View view) {
        super.detachView(view);
        for (final PresenterExtension extension : extensions) {
            extension.onDetachView();
        }
    }

    @CallSuper
    @Override
    public void destroyView(final View view) {
        super.destroyView(view);
        for (final PresenterExtension extension : extensions) {
            extension.onDestroyView();
        }
    }

    @CallSuper
    @Override
    public void onDestroy() {
        for (final PresenterExtension extension : extensions) {
            extension.onDestroy();
        }
        subscription.unsubscribe();
        super.onDestroy();
    }
}
