package io.github.dmitrikudrenko.sample.ui.video.queue.adapter;

import io.github.dmitrikudrenko.utils.ui.ViewHolder;

public interface VideoHolder extends ViewHolder {
    void showName(String value);
    void showThumbnail(String value);
    void showDuration(String value);
    void setSelected(boolean selected);
}
