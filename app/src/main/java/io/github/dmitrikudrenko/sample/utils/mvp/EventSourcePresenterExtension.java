package io.github.dmitrikudrenko.sample.utils.mvp;

import io.github.dmitrikudrenko.core.events.EventListener;
import io.github.dmitrikudrenko.core.events.EventSource;
import io.github.dmitrikudrenko.mdrxl.mvp.PresenterExtension;

public abstract class EventSourcePresenterExtension extends PresenterExtension implements EventListener {
    private final EventSource eventSource;

    public EventSourcePresenterExtension(final EventSource eventSource) {
        this.eventSource = eventSource;
    }

    @Override
    protected void onFirstAttach() {
        super.onFirstAttach();
        eventSource.register(this);
    }

    @Override
    protected void onDestroy() {
        eventSource.unregister(this);
        super.onDestroy();
    }
}
