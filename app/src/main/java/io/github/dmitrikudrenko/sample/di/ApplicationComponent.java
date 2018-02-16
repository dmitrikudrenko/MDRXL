package io.github.dmitrikudrenko.sample.di;

import io.github.dmitrikudrenko.cast.CastModule;
import io.github.dmitrikudrenko.core.di.CommandModule;
import io.github.dmitrikudrenko.core.di.CommonModule;
import io.github.dmitrikudrenko.core.di.NetworkModule;
import io.github.dmitrikudrenko.sample.cast.ExpandedControlsActivity;
import io.github.dmitrikudrenko.sample.di.video.VideoComponent;
import io.github.dmitrikudrenko.sample.di.video.VideoModule;
import io.github.dmitrikudrenko.sample.di.woman.WomanComponent;
import io.github.dmitrikudrenko.sample.di.woman.WomanModule;
import io.github.dmitrikudrenko.sample.ui.settings.SettingsFragment;
import io.github.dmitrikudrenko.sample.ui.video.list.CastingDialogFragment;
import io.github.dmitrikudrenko.sample.ui.video.list.WitcherVideosFragment;
import io.github.dmitrikudrenko.sample.ui.video.queue.VideoQueueFragment;
import io.github.dmitrikudrenko.sample.ui.women.details.GeraltWomanActivity;
import io.github.dmitrikudrenko.sample.ui.women.list.GeraltWomenFragment;
import io.github.dmitrikudrenko.sample.ui.women.photos.adapter.PhotoFragment;
import io.github.dmitrikudrenko.sample.utils.profiling.ProfilingProcessor;

import javax.inject.Singleton;
import java.util.Set;

@Singleton
@dagger.Component(
        modules = {
                CommonModule.class,
                NetworkModule.class,
                UiModule.class,
                ConfigurationModule.class,
                CommandModule.class,
                ProfilingModule.class,
                CastModule.class
        }
)
public interface ApplicationComponent {
    Set<ProfilingProcessor> profilingProcessors();

    WomanComponent plus(WomanModule module);

    GeraltWomenFragment.Component plus(GeraltWomenFragment.Module module);

    SettingsFragment.Component plus(SettingsFragment.Module module);

    VideoComponent plus(VideoModule module);

    WitcherVideosFragment.Component plus(WitcherVideosFragment.Module module);

    VideoQueueFragment.Component plus(VideoQueueFragment.Module module);

    ExpandedControlsActivity.Component plus(ExpandedControlsActivity.Module module);

    void inject(PhotoFragment fragment);

    void inject(CastingDialogFragment fragment);

    void inject(GeraltWomanActivity activity);
}
