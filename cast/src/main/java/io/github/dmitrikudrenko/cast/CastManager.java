package io.github.dmitrikudrenko.cast;

import android.net.Uri;
import android.support.annotation.VisibleForTesting;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaLoadOptions;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.images.WebImage;
import com.google.common.base.Strings;
import rx.Emitter;
import rx.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class CastManager {
    private final SessionManagerListenerImpl sessionManagerListener;

    private final List<OnSessionStartedListener> onSessionStartedListeners = new LinkedList<>();

    @Inject
    public CastManager(final SessionManagerListenerImpl l) {
        sessionManagerListener = l;
        sessionManagerListener.setOnSessionStartedListener(this::dispatchSessionStartedListeners);
    }

    @VisibleForTesting
    public void dispatchSessionStartedListeners() {
        for (final OnSessionStartedListener listener : onSessionStartedListeners) {
            listener.onSessionStarted();
        }
    }

    public void registerOnSessionStartedListener(final OnSessionStartedListener l) {
        onSessionStartedListeners.add(l);
    }

    public void unregisterOnSessionStartedListener(final OnSessionStartedListener l) {
        onSessionStartedListeners.remove(l);
    }

    private MediaInfo createMediaInfo(final MediaItem mediaItem) {
        final MediaMetadata metadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);
        metadata.putString(MediaMetadata.KEY_TITLE, mediaItem.title());
        metadata.putString(MediaMetadata.KEY_SUBTITLE, mediaItem.subtitle());
        if (!Strings.isNullOrEmpty(mediaItem.thumbnail())) {
            metadata.addImage(new WebImage(Uri.parse(mediaItem.thumbnail())));
        }

        return new MediaInfo.Builder(mediaItem.url())
                .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                .setContentType(mediaItem.contentType())
                .setStreamDuration(mediaItem.duration())
                .setMetadata(metadata)
                .build();
    }

    public void play(final MediaItem mediaItem) {
        if (!isCasting()) {
            return;
        }

        final MediaInfo mediaInfo = createMediaInfo(mediaItem);
        final RemoteMediaClient remoteMediaClient = sessionManagerListener.getRemoteMediaClient();
        final MediaLoadOptions mediaLoadOptions = new MediaLoadOptions.Builder()
                .setAutoplay(true).setPlayPosition(mediaItem.start()).build();
        remoteMediaClient.load(mediaInfo, mediaLoadOptions);
    }

    public void append(final MediaItem mediaItem) {
        if (!isCasting()) {
            return;
        }

        final RemoteMediaClient remoteMediaClient = sessionManagerListener.getRemoteMediaClient();
        final MediaStatus mediaStatus = remoteMediaClient.getMediaStatus();
        if (mediaStatus.getQueueItemCount() > 0) {
            final MediaInfo mediaInfo = createMediaInfo(mediaItem);
            final MediaQueueItem mediaQueueItem = new MediaQueueItem.Builder(mediaInfo).build();
            remoteMediaClient.queueAppendItem(mediaQueueItem, null);
        } else {
            play(mediaItem);
        }
    }

    public boolean isCasting() {
        return sessionManagerListener.isConnected();
    }

    public Observable<MediaStatus> getQueue() {
        if (isCasting()) {
            final RemoteMediaClient remoteMediaClient = sessionManagerListener.getRemoteMediaClient();
            return Observable.create(emitter -> {
                final RemoteMediaClientListenerAdapter l = new RemoteMediaClientListenerAdapter() {
                    @Override
                    public void onMetadataUpdated() {
                        emitMediaStatus(emitter, remoteMediaClient);
                    }
                };
                remoteMediaClient.addListener(l);
                emitter.setCancellation(() -> remoteMediaClient.removeListener(l));
                emitMediaStatus(emitter, remoteMediaClient);
            }, Emitter.BackpressureMode.BUFFER);
        } else {
            return Observable.empty();
        }
    }

    private void emitMediaStatus(final Emitter<MediaStatus> emitter,
                                 final RemoteMediaClient remoteMediaClient) {
        final MediaStatus mediaStatus = remoteMediaClient.getMediaStatus();
        if (mediaStatus != null) {
            emitter.onNext(mediaStatus);
        }
    }

    public void playFromQueue(final int itemId) {
        if (!isCasting()) {
            return;
        }
        final RemoteMediaClient remoteMediaClient = sessionManagerListener.getRemoteMediaClient();
        remoteMediaClient.queueJumpToItem(itemId, null);
    }

    public void deleteFromQueue(final int itemId) {
        if (!isCasting()) {
            return;
        }
        final RemoteMediaClient remoteMediaClient = sessionManagerListener.getRemoteMediaClient();
        remoteMediaClient.queueRemoveItem(itemId, null);
    }

    public void moveTo(final int itemId, final int position) {
        if (!isCasting()) {
            return;
        }
        final RemoteMediaClient remoteMediaClient = sessionManagerListener.getRemoteMediaClient();
        remoteMediaClient.queueMoveItemToNewIndex(itemId, position, null);
    }
}
