package io.github.dmitrikudrenko.sample.ui.video.player;

import com.arellomobile.mvp.InjectViewState;
import io.github.dmitrikudrenko.cast.CastManager;
import io.github.dmitrikudrenko.cast.ImmutableMediaItem;
import io.github.dmitrikudrenko.cast.MediaItem;
import io.github.dmitrikudrenko.core.local.cursor.WitcherVideoCursor;
import io.github.dmitrikudrenko.core.local.loader.video.WitcherVideoLoaderFactory;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderArguments;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaders;
import io.github.dmitrikudrenko.mdrxl.mvp.RxLoaderPresenter;
import io.github.dmitrikudrenko.sample.di.MultiWindow;
import io.github.dmitrikudrenko.sample.di.video.VideoId;
import io.github.dmitrikudrenko.sample.ui.video.CastExtension;
import io.github.dmitrikudrenko.sample.utils.ui.messages.MessageFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;

@InjectViewState
public class VideoPlayerPresenter extends RxLoaderPresenter<VideoPlayerView> {
    private static final String ARG_ID = "id";
    private static final int LOADER_ID = RxLoaders.generateId();

    private final WitcherVideoLoaderFactory loaderFactory;
    private final MessageFactory messageFactory;
    private final CastManager castManager;
    private final boolean multiwindow;
    private final long id;

    @Nullable
    private PlayerObserver playerObserver;

    private WitcherVideoCursor data;

    private long playerPosition;

    @Inject
    public VideoPlayerPresenter(final RxLoaderManager loaderManager,
                                final WitcherVideoLoaderFactory loaderFactory,
                                final MessageFactory messageFactory,
                                final CastManager castManager,
                                @MultiWindow final boolean multiwindow,
                                @VideoId final long id) {
        super(loaderManager);
        this.loaderFactory = loaderFactory;
        this.messageFactory = messageFactory;
        this.castManager = castManager;
        this.multiwindow = multiwindow;
        this.id = id;
        add(new CastExtension(castManager) {
            @Override
            protected void sessionStarted() {
                onCastStarted();
            }
        });
    }

    @Override
    public void attachView(final VideoPlayerView view) {
        super.attachView(view);
        if (view instanceof PlayerObserver) {
            playerObserver = (PlayerObserver) view;
        }

        final RxLoaderArguments args = new RxLoaderArguments();
        args.putLong(ARG_ID, id);
        getLoaderManager().init(LOADER_ID, args, new LoaderCallbacks());
    }

    @Override
    public void detachView(final VideoPlayerView view) {
        super.detachView(view);
        if (playerObserver == view) {
            playerPosition = playerObserver.getCurrentPosition();
            playerObserver = null;
        }
    }

    void onControlsShown() {
        if (!multiwindow) {
            getViewState().showActionBar();
        }
    }

    void onControlsHidden() {
        if (!multiwindow) {
            getViewState().hideActionBar();
        }
    }

    private void onDataLoaded(final WitcherVideoCursor data) {
        this.data = data;
        data.moveToFirst();
        getViewState().showVideo(data.getName(), data.getUrl(), playerPosition);
    }

    private void onCastStarted() {
        final MediaItem mediaItem = ImmutableMediaItem.builder()
                .url(data.getUrl())
                .title(data.getName())
                .thumbnail(data.getThumbnail())
                .duration(data.getDuration())
                .start(playerObserver != null ? playerObserver.getCurrentPosition() : 0)
                .build();
        castManager.play(mediaItem);
        getViewState().stopPlayer();
    }

    private class LoaderCallbacks extends RxLoaderCallbacks<WitcherVideoCursor> {

        @Override
        protected RxLoader<WitcherVideoCursor> getLoader(final int id, final RxLoaderArguments args) {
            return loaderFactory.create(args.getLong(ARG_ID));
        }

        @Override
        protected void onSuccess(final int id, final WitcherVideoCursor data) {
            onDataLoaded(data);
        }

        @Override
        protected void onError(final int id, final Throwable error) {
            messageFactory.showError(error.getMessage());
        }

    }
}
