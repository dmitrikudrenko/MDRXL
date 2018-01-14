package io.github.dmitrikudrenko.mdrxl.sample.utils.ui;

import android.util.Pair;
import android.view.View;

public class ViewUtils {
    public static Pair<Float, Float> getCenter(final View view) {
        return Pair.create(
                view.getX() + view.getWidth() / 2,
                view.getY() + view.getHeight() / 2
        );
    }
}
