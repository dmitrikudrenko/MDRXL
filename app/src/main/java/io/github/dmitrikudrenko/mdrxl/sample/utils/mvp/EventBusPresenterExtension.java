package io.github.dmitrikudrenko.mdrxl.sample.utils.mvp;

import io.github.dmitrikudrenko.mdrxl.mvp.PresenterExtension;
import io.github.dmitrikudrenko.mdrxl.sample.utils.EventBus;

public abstract class EventBusPresenterExtension extends PresenterExtension {
    private final EventBus eventBus;

    public EventBusPresenterExtension(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    protected void onFirstAttach() {
        super.onFirstAttach();
        eventBus.register(this);
    }

    @Override
    protected void onDestroy() {
        eventBus.unregister(this);
        super.onDestroy();
    }
}
