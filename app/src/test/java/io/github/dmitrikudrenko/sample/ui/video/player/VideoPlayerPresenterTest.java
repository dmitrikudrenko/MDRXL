package io.github.dmitrikudrenko.sample.ui.video.player;

import io.github.dmitrikudrenko.cast.CastManager;
import io.github.dmitrikudrenko.cast.MediaItem;
import io.github.dmitrikudrenko.cast.SessionManagerListenerImpl;
import io.github.dmitrikudrenko.core.local.cursor.WitcherVideoCursor;
import io.github.dmitrikudrenko.core.local.loader.video.WitcherVideoLoaderFactory;
import io.github.dmitrikudrenko.core.local.repository.WitcherVideoRepository;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderArguments;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.sample.ui.PresenterTest;
import io.github.dmitrikudrenko.sample.utils.ui.messages.MessageFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.robolectric.RobolectricTestRunner;
import rx.Observable;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class VideoPlayerPresenterTest extends PresenterTest {
    private static final String URL = "url";
    private static final String NAME = "name";
    private static final String THUMBNAIL = "thumbnail";
    private static final long DURATION = 100L;

    private VideoPlayerPresenter presenter;

    private WitcherVideoRepository repository;
    private CastManager castManager;

    private long id;

    private VideoPlayerView view;
    private VideoPlayerView$$State state;

    @Before
    public void setUp() {
        super.setUp();
        repository = mock(WitcherVideoRepository.class);
        messageFactory = mock(MessageFactory.class);
        castManager = spy(new CastManager(mock(SessionManagerListenerImpl.class)));
        id = 0;

        presenter = new VideoPlayerPresenter(
                rxLoaderManager,
                new WitcherVideoLoaderFactory(() -> activity, () -> repository),
                messageFactory,
                castManager,
                false,
                id
        );

        final WitcherVideoCursor video = mock(WitcherVideoCursor.class);
        when(video.getId()).thenReturn(id);
        when(video.getName()).thenReturn(NAME);
        when(video.getDuration()).thenReturn(DURATION);
        when(video.getThumbnail()).thenReturn(THUMBNAIL);
        when(video.getUrl()).thenReturn(URL);
        when(repository.getVideo(anyLong())).thenReturn(Observable.just(video));

        view = mock(VideoPlayerView.class);
        state = mock(VideoPlayerView$$State.class);

        presenter.setViewState(state);
        presenter.attachView(view);
    }

    @Test
    public void shouldLoadVideoOnAttach() {
        verify(rxLoaderManager).init(anyInt(), argThat(new ArgumentMatcher<RxLoaderArguments>() {
            @Override
            public boolean matches(final Object argument) {
                return argument instanceof RxLoaderArguments
                        && ((RxLoaderArguments) argument).getLong("id") == id;
            }
        }), any(RxLoaderCallbacks.class));
    }

    @Test
    public void shouldHideActionBarIfPlayerControlsHiding() {
        presenter.onControlsHidden();
        verify(state).hideActionBar();
    }

    @Test
    public void shouldShowActionBarIfPlayerControlsShowing() {
        presenter.onControlsShown();
        verify(state).showActionBar();
    }

    @Test
    public void shouldStartCastingIfSessionStarted() {
        await();

        castManager.dispatchSessionStartedListeners();
        verify(state).stopPlayer();
        verify(castManager).play(argThat(new ArgumentMatcher<MediaItem>() {
            @Override
            public boolean matches(final Object argument) {
                if (argument instanceof MediaItem) {
                    final MediaItem mediaItem = (MediaItem) argument;
                    return URL.equals(mediaItem.url()) &&
                            NAME.equals(mediaItem.title()) &&
                            THUMBNAIL.equals(mediaItem.thumbnail()) &&
                            DURATION == mediaItem.duration() &&
                            "videos/*".equals(mediaItem.contentType()) &&
                            mediaItem.subtitle() == null &&
                            mediaItem.start() == 0;
                }
                return false;
            }
        }));
    }
}
