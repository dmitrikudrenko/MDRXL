package io.github.dmitrikudrenko.mdrxl.sample.di;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

@Module
public class CommonModule {
    private final Context context;

    public CommonModule(final Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    Context provideContext() {
        return context;
    }
}
