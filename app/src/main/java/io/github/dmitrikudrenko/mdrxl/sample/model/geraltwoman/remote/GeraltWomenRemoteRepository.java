package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.remote;

import io.github.dmitrikudrenko.mdrxl.sample.model.UpdateModel;
import io.github.dmitrikudrenko.mdrxl.sample.settings.Settings;
import rx.Single;

import javax.inject.Inject;
import javax.inject.Provider;

public final class GeraltWomenRemoteRepository {
    private final Provider<Settings> settingsProvider;

    @Inject
    GeraltWomenRemoteRepository(final Provider<Settings> settingsProvider) {
        this.settingsProvider = settingsProvider;
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
}
