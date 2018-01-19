package io.github.dmitrikudrenko.sample.utils;

import android.util.Log;
import io.github.dmitrikudrenko.core.events.EventListener;
import io.github.dmitrikudrenko.core.events.EventSender;
import io.github.dmitrikudrenko.core.events.EventSource;

public class EventBus implements EventSender, EventSource {
    private static final String TAG = "EventBus";

    private final org.greenrobot.eventbus.EventBus eventBus;

    public EventBus(final org.greenrobot.eventbus.EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void post(final Object event) {
        try {
            eventBus.post(event);
        } catch (final Throwable th) {
            Log.e(TAG, th.getMessage(), th);
        }
    }

    @Override
    public void register(final EventListener listener) {
        eventBus.register(listener);
    }

    @Override
    public void unregister(final EventListener listener) {
        eventBus.unregister(listener);
    }
}
