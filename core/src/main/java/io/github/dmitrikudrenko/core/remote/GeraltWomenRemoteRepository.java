package io.github.dmitrikudrenko.core.remote;

import io.github.dmitrikudrenko.core.remote.model.Photos;
import io.github.dmitrikudrenko.core.remote.model.Woman;
import io.github.dmitrikudrenko.core.remote.model.Women;
import io.github.dmitrikudrenko.core.remote.settings.Settings;
import rx.Single;

import javax.inject.Inject;
import javax.inject.Provider;

public final class GeraltWomenRemoteRepository {
    private final Provider<Settings> settingsProvider;
    private final WomenApi womenApi;

    @Inject
    GeraltWomenRemoteRepository(final Provider<Settings> settingsProvider,
                                final WomenApi womenApi) {
        this.settingsProvider = settingsProvider;
        this.womenApi = womenApi;
    }

    public Single<Women> getWomen() {
        return womenApi.getWomen();
    }

    public Single<Photos> getPhotos(final long womanId) {
        return womenApi.getPhotos(womanId);
    }

    public Single<Boolean> save(final UpdateModel model) {
        return Single.just(settingsProvider.get())
                .flatMap(settings -> {
                    if (settings.isSuccess()) {
                        return Single.just(true);
                    } else if (settings.isTimeout()) {
                        return Single.error(new TimeoutException());
                    } else {
                        return Single.error(new RemoteException());
                    }
                });
    }

    public Single<Woman> getWoman(final long id) {
        return womenApi.getWoman(id);
    }
}
