package io.github.dmitrikudrenko.sample.ui.video.queue;

import android.support.v7.util.DiffUtil;
import com.arellomobile.mvp.InjectViewState;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.common.images.WebImage;
import io.github.dmitrikudrenko.cast.CastManager;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderArguments;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaders;
import io.github.dmitrikudrenko.mdrxl.mvp.RxLoaderPresenter;
import io.github.dmitrikudrenko.sample.cast.MediaQueue;
import io.github.dmitrikudrenko.sample.cast.MediaStatusLoader;
import io.github.dmitrikudrenko.sample.ui.video.queue.adapter.VideoHolder;
import io.github.dmitrikudrenko.sample.utils.ui.ClickInfo;
import io.github.dmitrikudrenko.sample.utils.ui.messages.MessageFactory;
import io.github.dmitrikudrenko.utils.common.ListDiffUtilCallback;
import io.github.dmitrikudrenko.utils.ui.RecyclerViewAdapterController;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@InjectViewState
public class VideoQueuePresenter extends RxLoaderPresenter<VideoQueueView>
        implements RecyclerViewAdapterController<VideoHolder>, VideoQueueItemOptionsListener {
    private static final int LOADER_ID = RxLoaders.generateId();

    private static final SimpleDateFormat durationFormat =
            new SimpleDateFormat("mm:ss", Locale.getDefault());

    private final CastManager castManager;
    private final MediaStatusLoader loader;
    private final MessageFactory messageFactory;

    private MediaQueue queue;

    private int selectedItemId = Integer.MIN_VALUE;

    @Inject
    public VideoQueuePresenter(final RxLoaderManager loaderManager,
                               final CastManager castManager,
                               final MediaStatusLoader loader,
                               final MessageFactory messageFactory) {
        super(loaderManager);
        this.castManager = castManager;
        this.loader = loader;
        this.messageFactory = messageFactory;
    }

    @Override
    public void attachView(final VideoQueueView view) {
        super.attachView(view);
        getLoaderManager().init(LOADER_ID, null, new LoaderCallbacks());
    }

    private void onDataLoaded(final MediaQueue queue) {
        final MediaQueue oldQueue = this.queue != null ? queue.copy() : null;
        this.queue = queue;
        this.selectedItemId = queue.getCurrentItemId();
        final DiffUtil.DiffResult result = DiffUtil
                .calculateDiff(new MediaStatusDiffUtilCallback(this.queue, oldQueue));
        getViewState().notifyDataChanged(result);
    }

    @Override
    public long getItemId(final int position) {
        return queue.get(position).getItemId();
    }

    @Override
    public int getItemCount() {
        return queue != null ? queue.size() : 0;
    }

    @Override
    public void bind(final VideoHolder holder, final int position) {
        final MediaQueueItem item = queue.get(position);
        final MediaInfo mediaInfo = item.getMedia();
        final MediaMetadata metadata = mediaInfo.getMetadata();
        final List<WebImage> images = metadata.getImages();

        holder.showName(metadata.getString(MediaMetadata.KEY_TITLE));
        holder.showDuration(durationFormat.format(new Date(mediaInfo.getStreamDuration())));
        if (!images.isEmpty()) {
            holder.showThumbnail(images.get(0).getUrl().toString());
        }
        holder.setSelected(selectedItemId == item.getItemId());
    }

    void onQueueItemSelected(final ClickInfo clickInfo) {
        final int position = clickInfo.getPosition();
        final int itemId = queue.get(position).getItemId();
        if (queue.getCurrentItemId() != itemId) {
            castManager.playFromQueue(itemId);

            final int oldSelectedItemId = selectedItemId;
            this.selectedItemId = itemId;

            final int oldSelectedItemPosition = queue.indexOf(oldSelectedItemId);
            if (oldSelectedItemPosition >= 0) {
                getViewState().notifyDataChanged(oldSelectedItemPosition);
            }
            getViewState().notifyDataChanged(clickInfo.getPosition());
        }
    }

    @Override
    public void onDeleteFromQueue(final int position) {
        castManager.deleteFromQueue(queue.get(position).getItemId());
    }

    @Override
    public void onPlayNext(final int position) {
        final int currentItemId = queue.getCurrentItemId();
        final int currentItemPosition = queue.indexOf(currentItemId);
        castManager.moveTo(queue.get(position).getItemId(), currentItemPosition + 1);
    }

    private class LoaderCallbacks extends RxLoaderCallbacks<MediaQueue> {

        @Override
        protected RxLoader<MediaQueue> getLoader(final int id, final RxLoaderArguments args) {
            return loader;
        }

        @Override
        protected void onSuccess(final int id, final MediaQueue data) {
            onDataLoaded(data);
        }

        @Override
        protected void onError(final int id, final Throwable error) {
            messageFactory.showError(error.getMessage());
        }
    }

    private static class MediaStatusDiffUtilCallback extends ListDiffUtilCallback<MediaQueueItem> {

        MediaStatusDiffUtilCallback(final List<MediaQueueItem> newList,
                                    @Nullable final List<MediaQueueItem> oldList) {
            super(newList, oldList);
        }

        @Override
        public boolean areItemsTheSame(final MediaQueueItem oldItem, final MediaQueueItem newItem) {
            return oldItem.getItemId() == newItem.getItemId();
        }

        @Override
        public boolean areContentsTheSame(final MediaQueueItem oldItem, final MediaQueueItem newItem) {
            return oldItem.getMedia().getMetadata().equals(newItem.getMedia().getMetadata());
        }
    }
}
