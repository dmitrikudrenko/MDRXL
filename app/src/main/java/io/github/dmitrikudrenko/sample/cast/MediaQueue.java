package io.github.dmitrikudrenko.sample.cast;

import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.MediaStatus;

import java.util.AbstractList;

public class MediaQueue extends AbstractList<MediaQueueItem> {
    private final MediaStatus mediaStatus;

    MediaQueue(final MediaStatus mediaStatus) {
        this.mediaStatus = mediaStatus;
    }

    @Override
    public MediaQueueItem get(final int i) {
        return mediaStatus.getQueueItem(i);
    }

    @Override
    public int size() {
        return mediaStatus.getQueueItemCount();
    }

    public int getCurrentItemId() {
        return mediaStatus.getCurrentItemId();
    }

    public MediaQueue copy() {
        return new MediaQueue(mediaStatus);
    }

    public int indexOf(final int itemId) {
        int index = 0;
        for (final MediaQueueItem item : this) {
            if (item.getItemId() == itemId) {
                return index;
            }
            index++;
        }
        return -1;
    }
}
