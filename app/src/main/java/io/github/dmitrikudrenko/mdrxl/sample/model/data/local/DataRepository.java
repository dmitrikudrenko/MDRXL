package io.github.dmitrikudrenko.mdrxl.sample.model.data.local;

import io.github.dmitrikudrenko.mdrxl.sample.model.data.Data;
import rx.Observable;
import rx.Single;
import rx.subjects.BehaviorSubject;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

@Singleton
public final class DataRepository {
    private static final int INTERVAL_SEC = 15;

    private final BehaviorSubject<Data> subject = BehaviorSubject.create();

    @Inject
    DataRepository() {
        subject.onNext(Data.create(0, "default name #0"));

        Observable.interval(INTERVAL_SEC, TimeUnit.SECONDS)
                .map(step -> {
                    int id = subject.getValue().getId();
                    return Data.create(id + 1, "default name #" + id);
                })
                .subscribe(subject);
    }

    public Observable<Data> get() {
        return subject;
    }

    public Single<Data> save(final Data data) {
        return Single.fromCallable(() -> {
            subject.onNext(data);
            return data;
        });
    }
}
