package io.github.dmitrikudrenko.cast;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaLoadOptions;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.robolectric.RobolectricTestRunner;
import rx.Observable;
import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class CastManagerTest {
    CastManager castManager;
    SessionManagerListenerImpl sessionManagerListener;
    RemoteMediaClient remoteMediaClient;

    @Before
    public void setUp() {
        sessionManagerListener = mock(SessionManagerListenerImpl.class);
        remoteMediaClient = mock(RemoteMediaClient.class);
        when(sessionManagerListener.getRemoteMediaClient()).thenReturn(remoteMediaClient);
        when(sessionManagerListener.isConnected()).thenReturn(true);
        castManager = spy(new CastManager(sessionManagerListener));
    }

    @Test
    public void shouldBeCasting() {
        assertTrue(castManager.isCasting());
    }

    @Test
    public void shouldPlayFromQueue() {
        castManager.playFromQueue(0);
        verify(remoteMediaClient).queueJumpToItem(eq(0), any(JSONObject.class));
    }

    @Test
    public void shouldDeleteFromQueue() {
        castManager.deleteFromQueue(0);
        verify(remoteMediaClient).queueRemoveItem(eq(0), any(JSONObject.class));
    }

    @Test
    public void shouldMoveQueueItemTo() {
        castManager.moveTo(0, 1);
        verify(remoteMediaClient).queueMoveItemToNewIndex(eq(0), eq(1), any(JSONObject.class));
    }

    @Test
    public void shouldAppendItemToQueue() {
        final MediaStatus mediaStatus = mock(MediaStatus.class);
        when(mediaStatus.getQueueItemCount()).thenReturn(1);
        when(remoteMediaClient.getMediaStatus()).thenReturn(mediaStatus);

        final MediaItem mediaItem = mock(MediaItem.class);
        when(mediaItem.title()).thenReturn("Name");
        when(mediaItem.thumbnail()).thenReturn("Thumbnail");
        when(mediaItem.url()).thenReturn("Url");
        when(mediaItem.duration()).thenReturn(600L);
        when(mediaItem.contentType()).thenReturn("videos/*");
        castManager.append(mediaItem);
        verify(remoteMediaClient).queueAppendItem(argThat(new ArgumentMatcher<MediaQueueItem>() {
            @Override
            public boolean matches(final Object argument) {
                if (argument instanceof MediaQueueItem) {
                    final MediaQueueItem item = (MediaQueueItem) argument;
                    final MediaInfo info = item.getMedia();
                    final MediaMetadata metadata = info.getMetadata();
                    return "Name".equals(metadata.getString(MediaMetadata.KEY_TITLE)) &&
                            "videos/*".equals(info.getContentType()) &&
                            600L == info.getStreamDuration();
                }
                return false;
            }
        }), any(JSONObject.class));
    }

    @Test
    public void shouldPlayItemIfQueueIsEmpty() {
        final MediaStatus mediaStatus = mock(MediaStatus.class);
        when(mediaStatus.getQueueItemCount()).thenReturn(0);
        when(remoteMediaClient.getMediaStatus()).thenReturn(mediaStatus);

        final MediaItem mediaItem = mock(MediaItem.class);
        when(mediaItem.title()).thenReturn("Name");
        when(mediaItem.thumbnail()).thenReturn("Thumbnail");
        when(mediaItem.url()).thenReturn("Url");
        when(mediaItem.duration()).thenReturn(600L);
        when(mediaItem.contentType()).thenReturn("videos/*");
        castManager.append(mediaItem);
        verify(remoteMediaClient).load(argThat(new ArgumentMatcher<MediaInfo>() {
            @Override
            public boolean matches(final Object argument) {
                if (argument instanceof MediaInfo) {
                    final MediaInfo info = (MediaInfo) argument;
                    final MediaMetadata metadata = info.getMetadata();
                    return "Name".equals(metadata.getString(MediaMetadata.KEY_TITLE)) &&
                            "videos/*".equals(info.getContentType()) &&
                            600L == info.getStreamDuration();
                }
                return false;
            }
        }), any(MediaLoadOptions.class));
    }

    @Test
    public void shouldReturnQueue() {
        final MediaStatus mediaStatus = mock(MediaStatus.class);
        when(mediaStatus.getQueueItemCount()).thenReturn(0);
        when(remoteMediaClient.getMediaStatus()).thenReturn(mediaStatus);

        final Observable<MediaStatus> queue = castManager.getQueue();
        final TestSubscriber<MediaStatus> subscriber = new TestSubscriber<>();
        queue.subscribe(subscriber);

        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertValue(mediaStatus);

        verify(remoteMediaClient).addListener(any(RemoteMediaClient.Listener.class));

        subscriber.unsubscribe();

        verify(remoteMediaClient).removeListener(any(RemoteMediaClient.Listener.class));
    }
}
