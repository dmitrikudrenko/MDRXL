package io.github.dmitrikudrenko.mdrxl.sample.di;

import io.github.dmitrikudrenko.mdrxl.sample.di.woman.WomanComponent;
import io.github.dmitrikudrenko.mdrxl.sample.di.woman.WomanModule;
import io.github.dmitrikudrenko.mdrxl.sample.ui.settings.SettingsFragment;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.list.GeraltWomenFragment;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.photos.adapter.PhotoFragment;

import javax.inject.Singleton;

@Singleton
@dagger.Component(modules = {CommonModule.class, NetworkModule.class, UiModule.class})
public interface ApplicationComponent {
    WomanComponent plus(WomanModule module);

    GeraltWomenFragment.Component plus(GeraltWomenFragment.Module module);

    SettingsFragment.Component plus(SettingsFragment.Module module);

    void inject(PhotoFragment fragment);
}
