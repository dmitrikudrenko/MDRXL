package io.github.dmitrikudrenko.mdrxl.sample.model.data.remote;

import io.github.dmitrikudrenko.mdrxl.sample.model.data.Data;
import io.github.dmitrikudrenko.mdrxl.sample.model.settings.Settings;
import rx.Observable;
import rx.Single;

import javax.inject.Inject;
import javax.inject.Provider;

public final class DataRemoteRepository {
    private final Provider<Settings> settingsProvider;

    @Inject
    DataRemoteRepository(final Provider<Settings> settingsProvider) {
        this.settingsProvider = settingsProvider;
    }

    public Single<Data> save(final Data data) {
        return Observable.just(settingsProvider.get())
                .flatMap(settings -> {
                    if (settings.isSuccess()) {
                        return Observable.just(data);
                    } else if (settings.isTimeout()) {
                        return Observable.error(new TimeoutException());
                    } else {
                        return Observable.error(new RemoteException());
                    }
                })
                .toSingle();
    }
}
