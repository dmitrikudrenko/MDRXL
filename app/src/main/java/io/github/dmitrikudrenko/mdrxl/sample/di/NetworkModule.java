package io.github.dmitrikudrenko.mdrxl.sample.di;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.remote.WomenApi;
import io.github.dmitrikudrenko.mdrxl.sample.settings.NetworkSettingsRepository;
import io.github.dmitrikudrenko.mdrxl.sample.settings.Settings;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import javax.inject.Singleton;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

@Module
final class NetworkModule {
    @NetworkSettingsRepository.NetworkPreferences
    @Provides
    SharedPreferences provideNetworkSharedPreferences(final Context context) {
        return getDefaultSharedPreferences(context);
    }

    @Provides
    Settings provideSettings(final NetworkSettingsRepository repository) {
        return repository.getSync();
    }

    @Provides
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @NetworkScheduler
    @Provides
    Scheduler provideScheduler() {
        return Schedulers.io();
    }

    @Provides
    Retrofit provideRetrofit(final Gson gson,
                             @NetworkScheduler final Scheduler networkScheduler) {
        return new Retrofit.Builder()
                .baseUrl("https://geralt-f5e41.firebaseio.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(networkScheduler))
                .build();
    }

    @Singleton
    @Provides
    WomenApi provideWomenApi(final Retrofit retrofit) {
        return retrofit.create(WomenApi.class);
    }

    @interface NetworkScheduler {
    }
}
