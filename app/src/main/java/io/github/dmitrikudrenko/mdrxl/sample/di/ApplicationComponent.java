package io.github.dmitrikudrenko.mdrxl.sample.di;

import dagger.Component;
import io.github.dmitrikudrenko.mdrxl.sample.ui.data.details.SampleActivity;
import io.github.dmitrikudrenko.mdrxl.sample.ui.data.list.DataListActivity;
import io.github.dmitrikudrenko.mdrxl.sample.ui.settings.SettingsActivity;

import javax.inject.Singleton;

@Singleton
@Component(modules = {CommonModule.class, NetworkModule.class})
public interface ApplicationComponent {
    SampleActivity.SampleComponent plus(SampleActivity.SampleModule module);

    DataListActivity.DataListComponent plus(DataListActivity.DataListModule module);

    SettingsActivity.SettingsComponent plus(SettingsActivity.SettingsModule module);
}
