package io.github.dmitrikudrenko.core.di;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import io.github.dmitrikudrenko.core.remote.WitcherApi;
import io.github.dmitrikudrenko.core.remote.settings.NetworkSettingsRepository;
import io.github.dmitrikudrenko.core.remote.settings.Settings;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import javax.inject.Singleton;
import java.util.Set;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

@Module
public final class NetworkModule {
    private static final long CACHE_SIZE = 100 * 1024 * 1024;

    @interface NetworkScheduler {
    }

    @NetworkPreferences
    @Provides
    public SharedPreferences provideNetworkSharedPreferences(final Context context) {
        return getDefaultSharedPreferences(context);
    }

    @Provides
    public Settings provideSettings(final NetworkSettingsRepository repository) {
        return repository.getSync();
    }

    @Provides
    public Gson provideGson() {
        return new GsonBuilder().create();
    }

    @NetworkScheduler
    @Provides
    public Scheduler provideScheduler() {
        return Schedulers.io();
    }

    @Provides
    public OkHttpClient provideHttpClient(final Context context, final Set<Interceptor> interceptors) {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        for (final Interceptor interceptor : interceptors) {
            builder.addNetworkInterceptor(interceptor);
        }
        return builder.cache(new Cache(context.getCacheDir(), CACHE_SIZE)).build();
    }

    @Provides
    public Retrofit provideRetrofit(final Gson gson,
                                    @NetworkScheduler final Scheduler networkScheduler,
                                    final OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://geralt-f5e41.firebaseio.com/")
                .callFactory(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(networkScheduler))
                .build();
    }

    @Singleton
    @Provides
    public WitcherApi provideWomenApi(final Retrofit retrofit) {
        return retrofit.create(WitcherApi.class);
    }

    @Provides
    @IntoSet
    public Interceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }
}
