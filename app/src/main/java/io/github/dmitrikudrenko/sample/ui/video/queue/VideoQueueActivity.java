package io.github.dmitrikudrenko.sample.ui.video.queue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import io.github.dmitrikudrenko.sample.ui.base.BaseFragmentHolderRxActivity;

public class VideoQueueActivity extends BaseFragmentHolderRxActivity<VideoQueueFragment> {
    public static Intent intent(final Context context) {
        return new Intent(context, VideoQueueActivity.class);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected VideoQueueFragment createFragment() {
        return new VideoQueueFragment();
    }
}
