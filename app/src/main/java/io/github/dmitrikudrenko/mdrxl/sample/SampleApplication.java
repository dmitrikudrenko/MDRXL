package io.github.dmitrikudrenko.mdrxl.sample;

import android.app.Application;
import io.github.dmitrikudrenko.mdrxl.sample.di.ApplicationComponent;
import io.github.dmitrikudrenko.mdrxl.sample.di.CommonModule;
import io.github.dmitrikudrenko.mdrxl.sample.di.DaggerApplicationComponent;

public class SampleApplication extends Application {
    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .commonModule(new CommonModule(this))
                .build();
    }

    public static ApplicationComponent get() {
        return applicationComponent;
    }
}
