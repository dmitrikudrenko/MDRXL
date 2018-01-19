package io.github.dmitrikudrenko.sample.utils.ui;

import android.view.View;

import javax.annotation.Nullable;

public class ClickInfo {
    private final int position;
    @Nullable
    private final View view;

    private ClickInfo(final int position, @Nullable final View view) {
        this.position = position;
        this.view = view;
    }

    public static ClickInfo clickInfo(final View view) {
        return new ClickInfo(-1, view);
    }

    public static ClickInfo clickInfo(final int position) {
        return new ClickInfo(position, null);
    }

    public static ClickInfo clickInfo(final int position, @Nullable final View view) {
        return new ClickInfo(position, view);
    }

    public int getPosition() {
        return position;
    }

    @Nullable
    public View getView() {
        return view;
    }
}
