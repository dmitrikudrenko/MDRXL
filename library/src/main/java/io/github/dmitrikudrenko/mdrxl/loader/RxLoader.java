package io.github.dmitrikudrenko.mdrxl.loader;

import android.content.Context;
import android.support.v4.content.Loader;
import android.util.Log;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

import javax.annotation.Nullable;

public abstract class RxLoader<D> extends Loader<RxLoaderData<D>> {
    @Nullable
    private BehaviorSubject<D> subject;
    @Nullable
    private Subscription subjectSubscription;
    @Nullable
    private Subscription subscription;

    public RxLoader(final Context context) {
        super(context);
    }

    protected abstract Observable<D> create();

    @Override
    protected void onStartLoading() {
        Log.d(getClass().getSimpleName(), "onStartLoading");
        super.onStartLoading();
        if (subject == null) {
            subject = BehaviorSubject.create();
        }

        subscription = subject.observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onResult, this::onError);

        if (subjectSubscription == null) {
            subjectSubscription = create().subscribeOn(Schedulers.io()).subscribe(subject);
        }
    }

    void onResult(final D data) {
        Log.d(getClass().getSimpleName(), "onResult " + data.toString());
        deliverResult(RxLoaderData.result(data));
    }

    private void onError(final Throwable error) {
        Log.d(getClass().getSimpleName(), "onError " + error.getMessage());
        deliverResult(RxLoaderData.error(error));
    }

    @Override
    protected void onStopLoading() {
        Log.d(getClass().getSimpleName(), "onStopLoading");
        super.onStopLoading();
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    @Override
    protected void onReset() {
        Log.d(getClass().getSimpleName(), "onReset");
        super.onReset();
        if (subjectSubscription != null) {
            subjectSubscription.unsubscribe();
        }
    }

    @Override
    public void onContentChanged() {
        if (subject != null) {
            final D data = subject.getValue();
            Log.d(getClass().getSimpleName(), "onContentChanged " + data.toString());
            deliverResult(RxLoaderData.result(data));
        }
    }
}
