package io.github.dmitrikudrenko.mdrxl.sample.utils.ui;

import android.widget.RadioGroup;

public class MuteableOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
    private final RadioGroup.OnCheckedChangeListener onCheckedChangeListener;
    private boolean mute;

    public MuteableOnCheckedChangeListener(final RadioGroup.OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    @Override
    public void onCheckedChanged(final RadioGroup group, final int checkedId) {
        if (!mute) {
            onCheckedChangeListener.onCheckedChanged(group, checkedId);
        }
    }

    public void mute() {
        this.mute = true;
    }

    public void unmute() {
        this.mute = false;
    }
}
