package io.github.dmitrikudrenko.sample.di.woman;

import dagger.Module;
import dagger.Provides;

@Module
public class WomanModule {
    private final long id;

    public WomanModule(final long id) {
        this.id = id;
    }

    @WomanScope
    @WomanId
    @Provides
    public long provideId() {
        return id;
    }
}
