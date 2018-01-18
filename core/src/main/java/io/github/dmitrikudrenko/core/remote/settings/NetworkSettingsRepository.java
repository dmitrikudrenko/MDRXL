package io.github.dmitrikudrenko.core.remote.settings;

import android.content.SharedPreferences;
import android.support.annotation.StringDef;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import io.github.dmitrikudrenko.core.di.NetworkPreferences;
import rx.Observable;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

import static io.github.dmitrikudrenko.core.remote.settings.NetworkSettingsRepository.NetworkPreference.*;

public final class NetworkSettingsRepository {
    private static final boolean DEFAULT_SUCCESS = true;
    private static final boolean DEFAULT_TIMEOUT = false;
    private static final boolean DEFAULT_ERROR = false;

    @StringDef({KEY_TIMEOUT, KEY_ERROR, KEY_SUCCESS})
    public @interface NetworkPreference {
        String KEY_SUCCESS = "success";
        String KEY_TIMEOUT = "timeout";
        String KEY_ERROR = "error";
    }

    private final SharedPreferences sharedPreferences;
    private final RxSharedPreferences rxSharedPreferences;

    @Inject
    NetworkSettingsRepository(@NetworkPreferences final SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.rxSharedPreferences = RxSharedPreferences.create(sharedPreferences);
    }

    public Settings getSync() {
        return new Settings(
                sharedPreferences.getBoolean(KEY_SUCCESS, DEFAULT_SUCCESS),
                sharedPreferences.getBoolean(KEY_TIMEOUT, DEFAULT_TIMEOUT),
                sharedPreferences.getBoolean(KEY_ERROR, DEFAULT_ERROR)
        );
    }

    public Observable<Settings> get() {
        return Observable
                .combineLatest(
                        rxSharedPreferences.getBoolean(KEY_SUCCESS, DEFAULT_SUCCESS).asObservable(),
                        rxSharedPreferences.getBoolean(KEY_TIMEOUT, DEFAULT_TIMEOUT).asObservable(),
                        rxSharedPreferences.getBoolean(KEY_ERROR, DEFAULT_ERROR).asObservable(),
                        Settings::new
                )
                .debounce(100, TimeUnit.MILLISECONDS)
                .map(settings -> settings.isValid() ? settings : Settings.success());
    }

    public void set(@NetworkPreference final String preference) {
        switch (preference) {
            case KEY_SUCCESS:
                doSet(Settings.success());
                break;
            case KEY_TIMEOUT:
                doSet(Settings.timeout());
                break;
            case KEY_ERROR:
                doSet(Settings.error());
                break;
        }
    }

    private void doSet(final Settings settings) {
        sharedPreferences.edit()
                .putBoolean(KEY_SUCCESS, settings.isSuccess())
                .putBoolean(KEY_TIMEOUT, settings.isTimeout())
                .putBoolean(KEY_ERROR, settings.isError())
                .apply();
    }
}
