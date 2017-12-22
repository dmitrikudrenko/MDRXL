package io.github.dmitrikudrenko.mdrxl.sample.di;

import io.github.dmitrikudrenko.mdrxl.sample.ui.data.details.DataDetailsFragment;
import io.github.dmitrikudrenko.mdrxl.sample.ui.data.list.DataListFragment;
import io.github.dmitrikudrenko.mdrxl.sample.ui.settings.SettingsFragment;

import javax.inject.Singleton;

@Singleton
@dagger.Component(modules = {CommonModule.class, NetworkModule.class})
public interface ApplicationComponent {
    DataDetailsFragment.Component plus(DataDetailsFragment.Module module);

    DataListFragment.Component plus(DataListFragment.Module module);

    SettingsFragment.Component plus(SettingsFragment.Module module);
}
