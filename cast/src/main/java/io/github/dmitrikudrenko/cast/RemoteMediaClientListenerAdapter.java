package io.github.dmitrikudrenko.cast;

import com.google.android.gms.cast.framework.media.RemoteMediaClient;

import static android.util.Log.v;

public abstract class RemoteMediaClientListenerAdapter implements RemoteMediaClient.Listener {
    private static final String TAG = "RemoteMediaClientL";

    @Override
    public void onStatusUpdated() {
        v(TAG, "onStatusUpdated");
    }

    @Override
    public void onMetadataUpdated() {
        v(TAG, "onMetadataUpdated");
    }

    @Override
    public void onQueueStatusUpdated() {
        v(TAG, "onQueueStatusUpdated");
    }

    @Override
    public void onPreloadStatusUpdated() {
        v(TAG, "onPreloadStatusUpdated");
    }

    @Override
    public void onSendingRemoteMediaRequest() {
        v(TAG, "onSendingRemoteMediaRequest");
    }

    @Override
    public void onAdBreakStatusUpdated() {
        v(TAG, "onAdBreakStatusUpdated");
    }
}
