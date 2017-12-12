package io.github.dmitrikudrenko.mdrxl.sample.di;

import android.content.Context;
import android.content.SharedPreferences;
import dagger.Module;
import dagger.Provides;
import io.github.dmitrikudrenko.mdrxl.sample.model.settings.NetworkSettingsRepository;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

@Module
public class NetworkModule {
    @NetworkSettingsRepository.NetworkPreferences
    @Provides
    public SharedPreferences provideNetworkSharedPreferences(final Context context) {
        return getDefaultSharedPreferences(context);
    }
}
