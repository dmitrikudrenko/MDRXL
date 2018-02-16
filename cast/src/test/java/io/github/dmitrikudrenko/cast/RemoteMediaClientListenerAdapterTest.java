package io.github.dmitrikudrenko.cast;

import org.junit.Before;
import org.junit.Test;

public class RemoteMediaClientListenerAdapterTest {
    private RemoteMediaClientListenerAdapter adapter;

    @Before
    public void setUp() {
        adapter = new RemoteMediaClientListenerAdapterImpl();
    }

    @Test
    public void shouldNotThrowAnyException() {
        adapter.onAdBreakStatusUpdated();
        adapter.onMetadataUpdated();
        adapter.onPreloadStatusUpdated();
        adapter.onQueueStatusUpdated();
        adapter.onSendingRemoteMediaRequest();
        adapter.onStatusUpdated();
    }

    private static class RemoteMediaClientListenerAdapterImpl extends RemoteMediaClientListenerAdapter {
    }
}
