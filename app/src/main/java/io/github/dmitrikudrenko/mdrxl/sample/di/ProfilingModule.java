package io.github.dmitrikudrenko.mdrxl.sample.di;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import io.github.dmitrikudrenko.mdrxl.sample.utils.profiling.ProfilingProcessor;
import io.github.dmitrikudrenko.mdrxl.sample.utils.profiling.TinyDancerProfilingProcessor;

@Module
class ProfilingModule {
    @IntoSet
    @Provides
    static ProfilingProcessor tinydancerProfilingProcessor(final Context context) {
        return new TinyDancerProfilingProcessor(context);
    }
}
