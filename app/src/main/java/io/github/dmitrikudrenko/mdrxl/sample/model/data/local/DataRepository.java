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
        subject.onNext(createStubData());

        Observable.interval(INTERVAL_SEC, TimeUnit.SECONDS)
                .map(step -> {
                    final Data data = subject.getValue();
                    return createNextData(data);
                })
                .subscribe(subject);
    }

    private Data createStubData() {
        final int id = 0;
        return new Data(id, "Name #" + id, "First attribute #" + id,
                "Second attribute #" + id, "Third attribute #" + id);
    }

    private Data createNextData(final Data data) {
        final int id = data.getId();
        final int nextId = id + 1;
        return new Data(nextId, "Name #" + nextId, "First attribute #" + nextId,
                "Second attribute #" + nextId, "Third attribute #" + nextId);
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
