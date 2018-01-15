package io.github.dmitrikudrenko.core.di;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

@Module
public final class CommonModule {
    private final Context context;

    public CommonModule(final Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    Context provideContext() {
        return context;
    }
}
