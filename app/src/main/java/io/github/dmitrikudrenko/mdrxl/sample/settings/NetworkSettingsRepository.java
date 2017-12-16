package io.github.dmitrikudrenko.mdrxl.sample.settings;

import android.content.SharedPreferences;
import android.support.annotation.StringDef;
import rx.Observable;
import rx.subjects.BehaviorSubject;

import javax.inject.Inject;
import javax.inject.Singleton;

import static io.github.dmitrikudrenko.mdrxl.sample.settings.NetworkSettingsRepository.NetworkPreference.*;

@Singleton
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

    private final BehaviorSubject<Settings> subject = BehaviorSubject.create();

    @Inject
    NetworkSettingsRepository(@NetworkPreferences final SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        setup();
    }

    private void setup() {
        subject.onNext(new Settings(
                sharedPreferences.getBoolean(KEY_SUCCESS, true),
                sharedPreferences.getBoolean(KEY_TIMEOUT, false),
                sharedPreferences.getBoolean(KEY_ERROR, false)
        ));
        sharedPreferences.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
            if (sharedPreferences.getBoolean(key, false)) {
                switch (key) {
                    case KEY_SUCCESS:
                        subject.onNext(Settings.success());
                        break;
                    case KEY_TIMEOUT:
                        subject.onNext(Settings.timeout());
                        break;
                    case KEY_ERROR:
                        subject.onNext(Settings.error());
                        break;
                }
            }
        });
    }

    public Settings getSync() {
        return subject.getValue();
    }

    public Observable<Settings> get() {
        return subject;
    }

    public void set(@NetworkPreference final String preference) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (preference) {
            case KEY_SUCCESS:
                editor.putBoolean(KEY_SUCCESS, true)
                        .putBoolean(KEY_TIMEOUT, false)
                        .putBoolean(KEY_ERROR, false);
                break;
            case KEY_TIMEOUT:
                editor.putBoolean(KEY_SUCCESS, false)
                        .putBoolean(KEY_TIMEOUT, true)
                        .putBoolean(KEY_ERROR, false);
                break;
            case KEY_ERROR:
                editor.putBoolean(KEY_SUCCESS, false)
                        .putBoolean(KEY_TIMEOUT, false)
                        .putBoolean(KEY_ERROR, true);
                break;
        }
        editor.apply();
    }
}
