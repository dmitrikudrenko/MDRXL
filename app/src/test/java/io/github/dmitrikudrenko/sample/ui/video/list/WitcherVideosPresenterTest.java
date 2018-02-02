package io.github.dmitrikudrenko.sample.ui.video.list;

import io.github.dmitrikudrenko.cast.CastManager;
import io.github.dmitrikudrenko.cast.MediaItem;
import io.github.dmitrikudrenko.core.commands.GeraltVideosUpdateCommandRequest;
import io.github.dmitrikudrenko.core.local.cursor.WitcherVideoCursor;
import io.github.dmitrikudrenko.core.local.loader.video.WitcherVideosLoader;
import io.github.dmitrikudrenko.core.local.repository.WitcherVideoRepository;
import io.github.dmitrikudrenko.sample.ui.PresenterTest;
import io.github.dmitrikudrenko.sample.utils.ui.ClickInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.robolectric.RobolectricTestRunner;
import rx.Observable;

import java.util.concurrent.TimeUnit;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class WitcherVideosPresenterTest extends PresenterTest {
    private WitcherVideosPresenter presenter;
    private WitcherVideoRepository videoRepository;
    private WitcherVideosLoader loader;
    private CastManager castManager;

    private WitcherVideosView$$State state;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        videoRepository = mock(WitcherVideoRepository.class);
        loader = new WitcherVideosLoader(activity, videoRepository);
        castManager = mock(CastManager.class);
    }

    @Test
    public void shouldStartUpdate() {
        attachPresenter(false);
        verify(commandStarter).execute(any(GeraltVideosUpdateCommandRequest.class));
    }

    private void attachPresenter(final boolean multiWindow) {
        presenter = new WitcherVideosPresenter(rxLoaderManager, () -> loader,
                commandStarter, multiWindow, router, messageFactory, castManager);

        state = mock(WitcherVideosView$$State.class);
        presenter.setViewState(state);

        final WitcherVideosView view = mock(WitcherVideosView.class);
        presenter.attachView(view);
    }

    @Test
    public void shouldShowCastingDialogWhileCasting() {
        createVideoData();

        attachPresenter(false);
        await();

        when(castManager.isCasting()).thenReturn(true);
        presenter.onItemSelected(ClickInfo.clickInfo(0));
        verify(state).showCastingDialog("Thumbnail");
    }

    @Test
    public void shouldPlayVideo() {
        createVideoData();

        attachPresenter(false);
        await();
        when(castManager.isCasting()).thenReturn(false);
        final ClickInfo clickInfo = ClickInfo.clickInfo(0);
        presenter.onItemSelected(clickInfo);

        presenter.play();

        verify(castManager).play(argThat(new ArgumentMatcher<MediaItem>() {
            @Override
            public boolean matches(final Object argument) {
                if (argument instanceof MediaItem) {
                    final MediaItem mediaItem = (MediaItem) argument;
                    return "Name".equals(mediaItem.title()) &&
                            "Thumbnail".equals(mediaItem.thumbnail()) &&
                            "Url".equals(mediaItem.url()) &&
                            TimeUnit.SECONDS.toMillis(600L) == mediaItem.duration();
                }
                return false;
            }
        }));
    }

    @Test
    public void shouldAddVideoToQueue() {
        createVideoData();

        attachPresenter(false);
        await();
        when(castManager.isCasting()).thenReturn(false);
        final ClickInfo clickInfo = ClickInfo.clickInfo(0);
        presenter.onItemSelected(clickInfo);

        presenter.addToQueue();

        verify(castManager).append(argThat(new ArgumentMatcher<MediaItem>() {
            @Override
            public boolean matches(final Object argument) {
                if (argument instanceof MediaItem) {
                    final MediaItem mediaItem = (MediaItem) argument;
                    return "Name".equals(mediaItem.title()) &&
                            "Thumbnail".equals(mediaItem.thumbnail()) &&
                            "Url".equals(mediaItem.url()) &&
                            TimeUnit.SECONDS.toMillis(600L) == mediaItem.duration();
                }
                return false;
            }
        }));
    }

    @Test
    public void shouldOpenPlayerIfNotCastingIfNotMultiWindow() {
        createVideoData();

        attachPresenter(false);
        await();

        when(castManager.isCasting()).thenReturn(false);
        final ClickInfo clickInfo = ClickInfo.clickInfo(0);
        presenter.onItemSelected(clickInfo);
        verify(router).openGeraltVideo(1L, clickInfo);
    }

    @Test
    public void shouldOpenPlayerIfNotCastingIfMultiWindow() {
        createVideoData();

        attachPresenter(true);
        await();

        when(castManager.isCasting()).thenReturn(false);
        final ClickInfo clickInfo = ClickInfo.clickInfo(0);
        presenter.onItemSelected(clickInfo);
        verify(state).notifyDataChanged(0);
        verify(router).openGeraltVideo(1L, clickInfo);
    }

    private void createVideoData() {
        final WitcherVideoCursor video = mock(WitcherVideoCursor.class);
        when(video.getId()).thenReturn(1L);
        when(video.getName()).thenReturn("Name");
        when(video.getThumbnail()).thenReturn("Thumbnail");
        when(video.getUrl()).thenReturn("Url");
        when(video.getDuration()).thenReturn(600L);
        when(video.moveToPosition(anyInt())).thenReturn(true);

        when(videoRepository.getVideos()).thenReturn(Observable.just(video));
    }
}
