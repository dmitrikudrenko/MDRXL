package io.github.dmitrikudrenko.sample.ui.video.list;

import android.support.v7.util.DiffUtil;
import com.arellomobile.mvp.InjectViewState;
import io.github.dmitrikudrenko.cast.CastManager;
import io.github.dmitrikudrenko.cast.ImmutableMediaItem;
import io.github.dmitrikudrenko.cast.MediaItem;
import io.github.dmitrikudrenko.core.commands.GeraltVideosUpdateCommandRequest;
import io.github.dmitrikudrenko.core.local.cursor.WitcherVideoCursor;
import io.github.dmitrikudrenko.core.local.loader.video.WitcherVideosLoader;
import io.github.dmitrikudrenko.mdrxl.commands.CommandStarter;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderArguments;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaders;
import io.github.dmitrikudrenko.mdrxl.mvp.RxLoaderPresenter;
import io.github.dmitrikudrenko.sample.di.MultiWindow;
import io.github.dmitrikudrenko.sample.ui.navigation.Router;
import io.github.dmitrikudrenko.sample.ui.video.list.adapter.WitcherVideoHolder;
import io.github.dmitrikudrenko.sample.utils.ui.ClickInfo;
import io.github.dmitrikudrenko.sample.utils.ui.messages.MessageFactory;
import io.github.dmitrikudrenko.utils.common.ListCursor;
import io.github.dmitrikudrenko.utils.common.ListDiffUtilCallback;
import io.github.dmitrikudrenko.utils.ui.RecyclerViewAdapterController;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@InjectViewState
public class WitcherVideosPresenter extends RxLoaderPresenter<WitcherVideosView>
        implements RecyclerViewAdapterController<WitcherVideoHolder> {
    private static final int LOADER_ID = RxLoaders.generateId();

    private static final SimpleDateFormat durationFormat =
            new SimpleDateFormat("mm:ss", Locale.getDefault());

    private final Provider<WitcherVideosLoader> loaderProvider;
    private final CommandStarter commandStarter;
    private final boolean multiWindow;
    private final Router router;
    private final MessageFactory messageFactory;
    private final CastManager castManager;

    private GeraltVideos data;

    private long selectedItemId = Integer.MIN_VALUE;
    private ClickInfo lastClickedInfo;

    @Inject
    public WitcherVideosPresenter(final RxLoaderManager loaderManager,
                                  final Provider<WitcherVideosLoader> loaderProvider,
                                  final CommandStarter commandStarter,
                                  @MultiWindow final boolean multiWindow,
                                  final Router router,
                                  final MessageFactory messageFactory,
                                  final CastManager castManager) {
        super(loaderManager);
        this.loaderProvider = loaderProvider;
        this.commandStarter = commandStarter;
        this.multiWindow = multiWindow;
        this.router = router;
        this.messageFactory = messageFactory;
        this.castManager = castManager;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        onRefresh();
    }

    @Override
    public void attachView(final WitcherVideosView view) {
        super.attachView(view);
        getLoaderManager().init(LOADER_ID, null, new LoaderCallbacks());
    }

    void onRefresh() {
        commandStarter.execute(new GeraltVideosUpdateCommandRequest());
        getViewState().stopLoading();
    }

    private void onDataLoaded(final GeraltVideos newData) {
        final GeraltVideos oldData = data != null ? data.copy() : null;
        final GeraltVideosDiffUtilCallback callback = new GeraltVideosDiffUtilCallback(newData, oldData);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callback);
        this.data = newData;

        getViewState().notifyDataSetChanged(diffResult);
    }

    @Override
    public long getItemId(final int position) {
        return data.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public void bind(final WitcherVideoHolder holder, final int position) {
        final WitcherVideoCursor video = data.get(position);
        holder.showName(video.getName());
        holder.showDuration(durationFormat.format(new Date(video.getDuration() * 1000)));
        holder.showThumbnail(video.getThumbnail());
    }

    void onItemSelected(final ClickInfo clickInfo) {
        this.lastClickedInfo = clickInfo;
        if (castManager.isCasting()) {
            getViewState().showCastingDialog(data.get(clickInfo.getPosition()).getThumbnail());
            return;
        }

        final long itemId = getItemId(clickInfo.getPosition());
        if (multiWindow) {
            if (itemId != selectedItemId) {
                setItemSelected(clickInfo.getPosition(), itemId);
                router.openGeraltVideo(itemId, clickInfo);
            }
        } else {
            router.openGeraltVideo(itemId, clickInfo);
        }
    }

    private void setItemSelected(final int position, final long itemId) {
        final long oldSelectedItemId = selectedItemId;
        this.selectedItemId = itemId;

        final int oldSelectedItemPosition = getPosition(oldSelectedItemId);
        if (oldSelectedItemPosition >= 0) {
            getViewState().notifyDataChanged(oldSelectedItemPosition);
        }
        getViewState().notifyDataChanged(position);
    }

    private int getPosition(final long itemId) {
        int index = 0;
        for (final WitcherVideoCursor cursor : data) {
            if (cursor.getId() == itemId) {
                return index;
            }
            index++;
        }
        return -1;
    }

    void play() {
        final WitcherVideoCursor video = data.get(lastClickedInfo.getPosition());
        final MediaItem mediaItem = createMediaItem(video);
        castManager.play(mediaItem);
    }

    void addToQueue() {
        final WitcherVideoCursor video = data.get(lastClickedInfo.getPosition());
        final MediaItem mediaItem = createMediaItem(video);
        castManager.append(mediaItem);
    }

    private MediaItem createMediaItem(final WitcherVideoCursor video) {
        return ImmutableMediaItem.builder()
                .url(video.getUrl())
                .title(video.getName())
                .thumbnail(video.getThumbnail())
                .duration(TimeUnit.SECONDS.toMillis(video.getDuration()))
                .build();
    }

    private static class GeraltVideos extends ListCursor<WitcherVideoCursor> {

        GeraltVideos(final WitcherVideoCursor cursor) {
            super(cursor);
        }

        GeraltVideos copy() {
            return new GeraltVideos(getCursor());
        }

    }

    private class LoaderCallbacks extends RxLoaderCallbacks<WitcherVideoCursor> {

        @Override
        protected RxLoader<WitcherVideoCursor> getLoader(final int id, final RxLoaderArguments args) {
            getViewState().startLoading();
            return loaderProvider.get();
        }

        @Override
        protected void onSuccess(final int id, final WitcherVideoCursor data) {
            getViewState().stopLoading();
            onDataLoaded(new GeraltVideos(data));
        }

        @Override
        protected void onError(final int id, final Throwable error) {
            getViewState().stopLoading();
            messageFactory.showError(error.getMessage());
        }
    }

    private static class GeraltVideosDiffUtilCallback extends ListDiffUtilCallback<WitcherVideoCursor> {

        GeraltVideosDiffUtilCallback(final List<WitcherVideoCursor> newList,
                                     @Nullable final List<WitcherVideoCursor> oldList) {
            super(newList, oldList);
        }

        @Override
        public boolean areItemsTheSame(final WitcherVideoCursor oldItem, final WitcherVideoCursor newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(final WitcherVideoCursor oldItem, final WitcherVideoCursor newItem) {
            return oldItem.areContentsTheSame(newItem);
        }
    }
}
