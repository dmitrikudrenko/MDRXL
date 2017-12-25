package io.github.dmitrikudrenko.mdrxl.sample;

import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;
import io.github.dmitrikudrenko.mdrxl.sample.di.ApplicationComponent;
import io.github.dmitrikudrenko.mdrxl.sample.di.CommonModule;
import io.github.dmitrikudrenko.mdrxl.sample.di.DaggerApplicationComponent;

public class SampleApplication extends MultiDexApplication {
    private static ApplicationComponent applicationComponent;

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
    }

    public static ApplicationComponent get() {
        return applicationComponent;
    }
}
