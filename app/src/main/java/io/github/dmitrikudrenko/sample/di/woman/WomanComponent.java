package io.github.dmitrikudrenko.sample.di.woman;

import dagger.Subcomponent;
import io.github.dmitrikudrenko.sample.ui.women.details.GeraltWomanFragment;
import io.github.dmitrikudrenko.sample.ui.women.photos.GeraltWomanPhotosFragment;

@WomanScope
@Subcomponent(modules = WomanModule.class)
public interface WomanComponent {
    @WomanId
    long id();

    GeraltWomanFragment.Component plus(GeraltWomanFragment.Module module);

    GeraltWomanPhotosFragment.Component plus(GeraltWomanPhotosFragment.Module module);
}
