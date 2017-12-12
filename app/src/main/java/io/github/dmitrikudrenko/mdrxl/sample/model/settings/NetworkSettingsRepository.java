package io.github.dmitrikudrenko.mdrxl.sample.model.settings;

import android.content.SharedPreferences;
import android.support.annotation.StringDef;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import javax.inject.Inject;
import javax.inject.Singleton;

import static io.github.dmitrikudrenko.mdrxl.sample.model.settings.NetworkSettingsRepository.NetworkPreference.*;

@Singleton
public final class NetworkSettingsRepository {
    public @interface NetworkPreferences {}

    @StringDef({KEY_TIMEOUT, KEY_ERROR, KEY_SUCCESS})
    public @interface NetworkPreference {
        String KEY_TIMEOUT = "timeout";
        String KEY_ERROR = "error";
        String KEY_SUCCESS = "success";
    }

    private final SharedPreferences sharedPreferences;

    @Inject
    public NetworkSettingsRepository(@NetworkPreferences final SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public Observable<Boolean> get(@NetworkPreference final String preference) {
        return asObservable(sharedPreferences, preference);
    }

    public void set(@NetworkPreference final String preference, final boolean value) {
        sharedPreferences.edit().putBoolean(preference, value).apply();
    }

    private Observable<Boolean> asObservable(final SharedPreferences preferences, final String preferenceKey) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                preferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(final SharedPreferences preferences, final String key) {
                        if (subscriber.isUnsubscribed()) {
                            preferences.unregisterOnSharedPreferenceChangeListener(this);
                        }
                        if (key.equals(preferenceKey)) {
                            produce(subscriber, preferences, preferenceKey);
                        }
                    }
                });
                produce(subscriber, preferences, preferenceKey);
            }

            private void produce(final Subscriber<? super Boolean> subscriber,
                                 final SharedPreferences preferences, final String key) {
                subscriber.onNext(preferences.getBoolean(key, false));
            }
        }).subscribeOn(Schedulers.io()).share();
    }
}
