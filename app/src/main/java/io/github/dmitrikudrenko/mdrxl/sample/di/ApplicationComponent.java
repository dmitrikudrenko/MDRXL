package io.github.dmitrikudrenko.mdrxl.sample.di;

import io.github.dmitrikudrenko.mdrxl.sample.ui.data.details.DataDetailsActivity;
import io.github.dmitrikudrenko.mdrxl.sample.ui.data.list.DataListActivity;
import io.github.dmitrikudrenko.mdrxl.sample.ui.settings.SettingsFragment;

import javax.inject.Singleton;

@Singleton
@dagger.Component(modules = {CommonModule.class, NetworkModule.class})
public interface ApplicationComponent {
    DataDetailsActivity.Component plus(DataDetailsActivity.Module module);

    DataListActivity.Component plus(DataListActivity.Module module);

    SettingsFragment.Component plus(SettingsFragment.Module module);
}
