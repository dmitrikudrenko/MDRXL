package io.github.dmitrikudrenko.sample.ui.video.queue;

import android.support.v7.util.DiffUtil;
import io.github.dmitrikudrenko.mdrxl.mvp.RxView;

public interface VideoQueueView extends RxView {
    void notifyDataChanged(DiffUtil.DiffResult result);

    void notifyDataChanged(int position);
}
