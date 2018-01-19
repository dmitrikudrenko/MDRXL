package io.github.dmitrikudrenko.sample.utils.profiling;

import android.content.Context;
import android.view.Gravity;
import com.codemonkeylabs.fpslibrary.TinyDancer;

public class TinyDancerProfilingProcessor implements ProfilingProcessor {
    private final Context context;
    private final boolean underTest;

    public TinyDancerProfilingProcessor(final Context context, final boolean underTest) {
        this.context = context;
        this.underTest = underTest;
    }

    @Override
    public void enable() {
        if (underTest) return;

        TinyDancer.create()
                .startingGravity(Gravity.BOTTOM | Gravity.END)
                .show(context);
    }

    @Override
    public void disable() {
        TinyDancer.hide(context);
    }
}
