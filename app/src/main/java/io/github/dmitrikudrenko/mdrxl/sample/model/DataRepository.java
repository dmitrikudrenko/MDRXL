package io.github.dmitrikudrenko.mdrxl.sample.model;

import android.util.Log;
import rx.Completable;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

@Singleton
public final class DataRepository {
    private static final int INTERVAL_SEC = 15;
    private static final int SAVE_PAUSE_MS = 1000;

    private final BehaviorSubject<Data> subject = BehaviorSubject.create();

    @Inject
    DataRepository() {
        subject.onNext(Data.create(0));

        Observable.interval(INTERVAL_SEC, TimeUnit.SECONDS)
                .map(step -> Data.create(subject.getValue().getId() + 1))
                .subscribe(subject);
    }

    public Observable<Data> get() {
        return subject.subscribeOn(Schedulers.io());
    }

    public void save(final Data data) {
        Completable.fromAction(() -> {
            try {
                Thread.sleep(SAVE_PAUSE_MS);
                subject.onNext(data);
            } catch (final InterruptedException e) {
                Log.e("DataRepository", "save", e);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }
}
