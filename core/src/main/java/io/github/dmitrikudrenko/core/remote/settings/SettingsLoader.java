package io.github.dmitrikudrenko.core.remote.settings;

import android.content.Context;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import rx.Observable;

import javax.inject.Inject;

public final class SettingsLoader extends RxLoader<Settings> {
    private final NetworkSettingsRepository networkSettingsRepository;

    @Inject
    SettingsLoader(final Context context, final NetworkSettingsRepository networkSettingsRepository) {
        super(context);
        this.networkSettingsRepository = networkSettingsRepository;
    }

    @Override
    protected Observable<Settings> create(final String query) {
        return networkSettingsRepository.get();
    }
}
