package io.github.dmitrikudrenko.sample.ui.video.queue;

public interface VideoQueueItemOptionsListener {
    void onDeleteFromQueue(int position);

    void onPlayNext(int position);
}
