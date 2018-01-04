package io.github.dmitrikudrenko.mdrxl.sample.utils;

import android.util.Log;

public class EventBus {
    private static final String TAG = "EventBus";

    private final org.greenrobot.eventbus.EventBus eventBus;

    public EventBus(final org.greenrobot.eventbus.EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void register(final Object object) {
        eventBus.register(object);
    }

    public void unregister(final Object object) {
        eventBus.unregister(object);
    }

    public void post(final Object event) {
        try {
            eventBus.post(event);
        } catch (final Throwable th) {
            Log.e(TAG, th.getMessage(), th);
        }
    }
}
