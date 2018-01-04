package io.github.dmitrikudrenko.mdrxl.sample.di;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import io.github.dmitrikudrenko.mdrxl.sample.utils.EventBus;

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

    @Provides
    EventBus provideEventBus() {
        return new EventBus(org.greenrobot.eventbus.EventBus.getDefault());
    }
}
