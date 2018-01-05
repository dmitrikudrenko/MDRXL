package io.github.dmitrikudrenko.mdrxl.sample.utils.profiling;

import android.content.Context;
import com.codemonkeylabs.fpslibrary.TinyDancer;

public class TinyDancerProfilingProcessor implements ProfilingProcessor {
    private final Context context;

    public TinyDancerProfilingProcessor(final Context context) {
        this.context = context;
    }

    @Override
    public void enable() {
        TinyDancer.create().show(context);
    }

    @Override
    public void disable() {
        TinyDancer.hide(context);
    }
}
