package io.github.dmitrikudrenko.mdrxl.sample.model.data.remote;

import io.github.dmitrikudrenko.mdrxl.sample.model.data.Data;
import io.github.dmitrikudrenko.mdrxl.sample.model.settings.NetworkSettingsRepository;
import rx.Observable;
import rx.Single;

import javax.inject.Inject;

public class DataRemoteRepository {
    private final NetworkSettingsRepository networkSettingsRepository;

    @Inject
    DataRemoteRepository(final NetworkSettingsRepository networkSettingsRepository) {
        this.networkSettingsRepository = networkSettingsRepository;
    }

    public Single<Data> save(final Data data) {
        return networkSettingsRepository.get()
                .take(1)
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
