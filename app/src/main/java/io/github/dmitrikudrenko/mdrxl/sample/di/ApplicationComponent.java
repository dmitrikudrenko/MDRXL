package io.github.dmitrikudrenko.mdrxl.sample.di;

import io.github.dmitrikudrenko.mdrxl.sample.di.woman.WomanComponent;
import io.github.dmitrikudrenko.mdrxl.sample.di.woman.WomanModule;
import io.github.dmitrikudrenko.mdrxl.sample.ui.settings.SettingsFragment;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.details.GeraltWomanActivity;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.list.GeraltWomenFragment;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.photos.adapter.PhotoFragment;
import io.github.dmitrikudrenko.mdrxl.sample.utils.profiling.ProfilingProcessor;

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
                ProfilingModule.class
        }
)
public interface ApplicationComponent {
    Set<ProfilingProcessor> profilingProcessors();

    WomanComponent plus(WomanModule module);

    GeraltWomenFragment.Component plus(GeraltWomenFragment.Module module);

    SettingsFragment.Component plus(SettingsFragment.Module module);

    void inject(PhotoFragment fragment);

    void inject(GeraltWomanActivity activity);
}
