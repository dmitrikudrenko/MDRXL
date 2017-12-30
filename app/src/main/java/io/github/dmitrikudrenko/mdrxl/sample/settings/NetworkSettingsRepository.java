package io.github.dmitrikudrenko.mdrxl.sample.settings;

import android.content.SharedPreferences;
import android.support.annotation.StringDef;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import rx.Observable;

import javax.inject.Inject;

import static io.github.dmitrikudrenko.mdrxl.sample.settings.NetworkSettingsRepository.NetworkPreference.*;

public final class NetworkSettingsRepository {
    public @interface NetworkPreferences {
    }

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

    @SuppressWarnings("ConstantConditions")
    public Settings getSync() {
        return new Settings(
                rxSharedPreferences.getBoolean(KEY_SUCCESS,true).get(),
                rxSharedPreferences.getBoolean(KEY_TIMEOUT, false).get(),
                rxSharedPreferences.getBoolean(KEY_ERROR, false).get()
        );
    }

    public Observable<Settings> get() {
        return Observable.combineLatest(
                rxSharedPreferences.getBoolean(KEY_SUCCESS).asObservable(),
                rxSharedPreferences.getBoolean(KEY_TIMEOUT).asObservable(),
                rxSharedPreferences.getBoolean(KEY_ERROR).asObservable(),
                Settings::new
        ).filter(Settings::isValid);
    }

    public void set(@NetworkPreference final String preference) {
        doSet(preference);
    }

    private void doSet(@NetworkPreference final String preference) {
        Settings newSettings = null;
        switch (preference) {
            case KEY_SUCCESS:
                newSettings = Settings.success();
                break;
            case KEY_TIMEOUT:
                newSettings = Settings.timeout();
                break;
            case KEY_ERROR:
                newSettings = Settings.error();
                break;
        }

        if (newSettings != null) {
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KEY_SUCCESS, newSettings.isSuccess())
                    .putBoolean(KEY_TIMEOUT, newSettings.isTimeout())
                    .putBoolean(KEY_ERROR, newSettings.isError());
            editor.apply();
        }
    }
}
