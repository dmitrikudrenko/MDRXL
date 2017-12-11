package io.github.dmitrikudrenko.mdrxl.sample.di;

import dagger.Component;
import io.github.dmitrikudrenko.mdrxl.sample.ui.SampleActivity;

@Component(modules = {CommonModule.class})
public interface ApplicationComponent {
    SampleActivity.SampleComponent plus(SampleActivity.SampleModule module);
}
