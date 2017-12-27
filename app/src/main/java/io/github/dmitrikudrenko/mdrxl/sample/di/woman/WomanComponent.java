package io.github.dmitrikudrenko.mdrxl.sample.di.woman;

import dagger.Subcomponent;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.details.GeraltWomanFragment;

@WomanScope
@Subcomponent(modules = WomanModule.class)
public interface WomanComponent {
    @WomanId
    long id();

    GeraltWomanFragment.Component plus(GeraltWomanFragment.Module module);
}
