package io.github.dmitrikudrenko.sample.ui.video.queue;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.MediaStatus;
import io.github.dmitrikudrenko.cast.CastManager;
import io.github.dmitrikudrenko.sample.cast.MediaStatusLoader;
import io.github.dmitrikudrenko.sample.ui.PresenterTest;
import io.github.dmitrikudrenko.sample.utils.ui.ClickInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import rx.Observable;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class VideoQueuePresenterTest extends PresenterTest {
    CastManager castManager;

    VideoQueueView view = mock(VideoQueueView.class);
    VideoQueueView$$State state = mock(VideoQueueView$$State.class);
    VideoQueuePresenter presenter;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        castManager = mock(CastManager.class);
        presenter = new VideoQueuePresenter(rxLoaderManager, castManager,
                new MediaStatusLoader(activity, castManager), messageFactory);
    }

    private void setupPresenter() {
        presenter.setViewState(state);
        presenter.attachView(view);
    }

    @Test
    public void shouldDeleteItemFromQueue() {
        final int itemId = 1;
        final MediaStatus mediaStatus = CastManagerFactory.createMediaStatus(itemId, new int[]{itemId});

        when(castManager.getQueue()).thenReturn(Observable.just(mediaStatus));

        setupPresenter();
        await();

        presenter.onDeleteFromQueue(0);

        verify(castManager).deleteFromQueue(itemId);
    }

    @Test
    public void shouldPlayNextItem() {
        final MediaStatus mediaStatus = CastManagerFactory.createMediaStatus(1, new int[]{1, 2, 3});

        when(castManager.getQueue()).thenReturn(Observable.just(mediaStatus));

        setupPresenter();
        await();

        presenter.onPlayNext(2);

        verify(castManager).moveTo(3, 1);
    }

    @Test
    public void shouldPlayAnotherVideoFromQueue() {
        final MediaStatus mediaStatus = CastManagerFactory.createMediaStatus(1, new int[]{1, 2, 3});

        when(castManager.getQueue()).thenReturn(Observable.just(mediaStatus));

        setupPresenter();
        await();

        presenter.onQueueItemSelected(ClickInfo.clickInfo(2));

        verify(castManager).playFromQueue(3);
        verify(state).notifyDataChanged(2);
    }

    private static class CastManagerFactory {
        static MediaStatus createMediaStatus(final int currentItemId, final int[] items) {
            final MediaStatus mediaStatus = mock(MediaStatus.class);
            when(mediaStatus.getQueueItemCount()).thenReturn(items.length);
            when(mediaStatus.getCurrentItemId()).thenReturn(currentItemId);
            for (int i = 0; i < items.length; i++) {
                final int itemId = items[i];
                final MediaInfo mediaInfo = mock(MediaInfo.class);
                when(mediaInfo.getMetadata()).thenReturn(mock(MediaMetadata.class));
                final MediaQueueItem queueItem = spy(new MediaQueueItem.Builder(mediaInfo).build());
                when(queueItem.getItemId()).thenReturn(itemId);

                when(mediaStatus.getQueueItem(i)).thenReturn(queueItem);
            }
            return mediaStatus;
        }
    }
}
