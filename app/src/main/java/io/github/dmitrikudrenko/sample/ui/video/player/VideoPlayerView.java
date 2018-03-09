package io.github.dmitrikudrenko.sample.ui.video.player;

import io.github.dmitrikudrenko.mdrxl.mvp.RxView;

public interface VideoPlayerView extends RxView {
    void showVideo(String title, String url, long startPosition);

    void hideActionBar();

    void showActionBar();

    void stopPlayer();

    void pausePlayer();
}
