package io.github.dmitrikudrenko.cast;

import org.immutables.value.Value;

import javax.annotation.Nullable;

@Value.Immutable
public abstract class MediaItem {
    public abstract String url();

    @Value.Default
    public String contentType() {
        return "videos/*";
    }

    public abstract String title();

    @Nullable
    public abstract String subtitle();

    @Nullable
    public abstract String thumbnail();

    @Value.Default
    public long duration() {
        return 0;
    }

    @Value.Default
    public long start() {
        return 0;
    }
}
