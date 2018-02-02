package io.github.dmitrikudrenko.core.remote;

import io.github.dmitrikudrenko.core.remote.model.video.Videos;
import io.github.dmitrikudrenko.core.remote.model.woman.Woman;
import io.github.dmitrikudrenko.core.remote.model.woman.Women;
import io.github.dmitrikudrenko.core.remote.model.woman.photo.Photos;
import io.github.dmitrikudrenko.core.remote.settings.Settings;
import rx.Single;

import javax.inject.Inject;
import javax.inject.Provider;

public final class WitcherRemoteRepository {
    private final Provider<Settings> settingsProvider;
    private final WitcherApi witcherApi;

    @Inject
    public WitcherRemoteRepository(final Provider<Settings> settingsProvider,
                                   final WitcherApi witcherApi) {
        this.settingsProvider = settingsProvider;
        this.witcherApi = witcherApi;
    }

    public Single<Women> getWomen() {
        return witcherApi.getWomen();
    }

    public Single<Photos> getPhotos(final long womanId) {
        return witcherApi.getPhotos(womanId);
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
        return witcherApi.getWoman(id);
    }

    public Single<Videos> getVideos() {
        return witcherApi.getVideos();
    }
}
