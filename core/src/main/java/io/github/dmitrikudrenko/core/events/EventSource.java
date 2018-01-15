package io.github.dmitrikudrenko.core.events;

public interface EventSource {
    void register(EventListener listener);

    void unregister(EventListener listener);
}
