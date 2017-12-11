package io.github.dmitrikudrenko.mdrxl.sample.model;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

public final class DataRepository {
    private static final int INTERVAL_SEC = 30;

    private final BehaviorSubject<Data> subject = BehaviorSubject.create();

    @Inject
    DataRepository() {
        Observable.interval(INTERVAL_SEC, TimeUnit.SECONDS)
                .map(step -> Data.create(step.intValue()))
                .subscribe(subject);
    }

    public Observable<Data> get() {
        return subject.subscribeOn(Schedulers.io());
    }
}
