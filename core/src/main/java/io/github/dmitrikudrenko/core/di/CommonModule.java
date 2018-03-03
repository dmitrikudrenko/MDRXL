package io.github.dmitrikudrenko.core.di;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import dagger.Module;
import dagger.Provides;

import javax.annotation.Nullable;

@Module
public final class CommonModule {
    private final Context context;

    @Nullable
    private AppCompatActivity activity;

    public CommonModule(final Context context) {
        final Application application = (Application) context.getApplicationContext();
        this.context = application;
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(final Activity a, final Bundle savedInstanceState) {
                activity = (AppCompatActivity) a;
            }

            @Override
            public void onActivityStarted(final Activity a) {
                activity = (AppCompatActivity) a;
            }

            @Override
            public void onActivityResumed(final Activity a) {
                activity = (AppCompatActivity) a;
            }

            @Override
            public void onActivityPaused(final Activity a) {
                if (activity == a) {
                    activity = null;
                }
            }

            @Override
            public void onActivityStopped(final Activity a) {
                if (activity == a) {
                    activity = null;
                }
            }

            @Override
            public void onActivitySaveInstanceState(final Activity a, final Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(final Activity a) {
                if (activity == a) {
                    activity = null;
                }
            }
        });
    }

    @Provides
    Context provideContext() {
        return context;
    }

    @Nullable
    @Provides
    AppCompatActivity provideActivity() {
        return activity;
    }
}
