package io.github.dmitrikudrenko.mdrxl.sample.ui.women.photos;

import com.arellomobile.mvp.InjectViewState;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.mvp.RxPresenter;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.photos.di.FragmentScope;

import javax.inject.Inject;

@FragmentScope
@InjectViewState
public class GeraltWomanPhotosPresenter extends RxPresenter<GeraltWomanPhotosView> {
    @Inject
    public GeraltWomanPhotosPresenter(final RxLoaderManager loaderManager) {
        super(loaderManager);
    }
}
