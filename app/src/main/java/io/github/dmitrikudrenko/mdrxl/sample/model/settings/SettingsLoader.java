package io.github.dmitrikudrenko.mdrxl.sample.model.settings;

import android.content.Context;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import rx.Observable;

import javax.inject.Inject;

public final class SettingsLoader extends RxLoader<Settings> {
    private final NetworkSettingsRepository networkPreferences;

    @Inject
    SettingsLoader(final Context context, final NetworkSettingsRepository networkPreferences) {
        super(context);
        this.networkPreferences = networkPreferences;
    }

    @Override
    protected Observable<Settings> create() {
        return Observable.combineLatest(
                networkPreferences.get(NetworkSettingsRepository.NetworkPreference.KEY_SUCCESS),
                networkPreferences.get(NetworkSettingsRepository.NetworkPreference.KEY_TIMEOUT),
                networkPreferences.get(NetworkSettingsRepository.NetworkPreference.KEY_ERROR),
                Settings::new
        );
    }
}
