package io.github.dmitrikudrenko.mdrxl.sample.di;

import io.github.dmitrikudrenko.mdrxl.sample.ui.settings.SettingsFragment;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.details.GeraltWomanFragment;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.list.GeraltWomenFragment;

import javax.inject.Singleton;

@Singleton
@dagger.Component(modules = {CommonModule.class, NetworkModule.class, UiModule.class})
public interface ApplicationComponent {
    GeraltWomanFragment.Component plus(GeraltWomanFragment.Module module);

    GeraltWomenFragment.Component plus(GeraltWomenFragment.Module module);

    SettingsFragment.Component plus(SettingsFragment.Module module);
}
