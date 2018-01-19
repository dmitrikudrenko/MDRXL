package io.github.dmitrikudrenko.sample.di;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import io.github.dmitrikudrenko.sample.utils.profiling.ProfilingProcessor;
import io.github.dmitrikudrenko.sample.utils.profiling.TinyDancerProfilingProcessor;

@Module
class ProfilingModule {
    @IntoSet
    @Provides
    static ProfilingProcessor tinydancerProfilingProcessor(final Context context,
                                                           @UnderTest final boolean underTest) {
        return new TinyDancerProfilingProcessor(context, underTest);
    }
}
