package io.github.dmitrikudrenko.sample.di.video;

import dagger.Module;
import dagger.Provides;

@Module
public class VideoModule {
    private final long id;

    public VideoModule(final long id) {
        this.id = id;
    }

    @VideoScope
    @VideoId
    @Provides
    public long provideId() {
        return id;
    }
}
