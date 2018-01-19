package io.github.dmitrikudrenko.sample;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import io.github.dmitrikudrenko.core.di.CommonModule;
import io.github.dmitrikudrenko.sample.di.ApplicationComponent;
import io.github.dmitrikudrenko.sample.di.DaggerApplicationComponent;
import io.github.dmitrikudrenko.sample.di.woman.WomanComponent;
import io.github.dmitrikudrenko.sample.di.woman.WomanModule;
import io.github.dmitrikudrenko.sample.utils.profiling.ProfilingProcessor;

public class SampleApplication extends Application {
    private static ApplicationComponent applicationComponent;
    private static WomanComponent womanComponent;

    private static final StrictMode.ThreadPolicy DISK_THREAD_POLICY =
            new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyFlashScreen()
                    .build();

    private static final StrictMode.VmPolicy DISK_VM_POLICY =
            new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build();

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(DISK_THREAD_POLICY);
            StrictMode.setVmPolicy(DISK_VM_POLICY);
        }

        applicationComponent = DaggerApplicationComponent.builder()
                .commonModule(new CommonModule(this))
                .build();

        for (final ProfilingProcessor processor : applicationComponent.profilingProcessors()) {
            processor.enable();
        }
    }

    @Override
    protected void attachBaseContext(final Context base) {
        try {
            super.attachBaseContext(base);
        } catch (final RuntimeException exception) {
            try {
                Class.forName("org.robolectric.Robolectric");
            } catch (final ClassNotFoundException ex) {
                throw exception;
            }
        }
    }

    public static ApplicationComponent get() {
        return applicationComponent;
    }

    public static WomanComponent createWomanComponent(final long id) {
        womanComponent = applicationComponent.plus(new WomanModule(id));
        return womanComponent;
    }

    public static WomanComponent getWomanComponent() {
        return womanComponent;
    }
}
