package io.github.dmitrikudrenko.mdrxl.sample.ui.women.photos;

import com.arellomobile.mvp.InjectViewState;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderArguments;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaders;
import io.github.dmitrikudrenko.mdrxl.mvp.RxPresenter;
import io.github.dmitrikudrenko.mdrxl.sample.di.FragmentScope;
import io.github.dmitrikudrenko.mdrxl.sample.di.woman.WomanId;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomanPhotoCursor;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomanPhotoLoaderFactory;

import javax.inject.Inject;

@FragmentScope
@InjectViewState
public class GeraltWomanPhotosPresenter extends RxPresenter<GeraltWomanPhotosView> {
    private static final String ARG_ID = "id";
    private static final int LOADER_ID = RxLoaders.generateId();

    private final GeraltWomanPhotoLoaderFactory loaderFactory;
    private final long womanId;

    @Inject
    GeraltWomanPhotosPresenter(final RxLoaderManager loaderManager,
                               final GeraltWomanPhotoLoaderFactory loaderFactory,
                               @WomanId final long womanId) {
        super(loaderManager);
        this.loaderFactory = loaderFactory;
        this.womanId = womanId;
    }

    @Override
    public void attachView(final GeraltWomanPhotosView view) {
        super.attachView(view);
        final RxLoaderArguments args = new RxLoaderArguments();
        args.putLong(ARG_ID, womanId);
        getLoaderManager().init(LOADER_ID, args, new LoaderCallbacks());
    }

    private class LoaderCallbacks extends RxLoaderCallbacks<GeraltWomanPhotoCursor> {

        @Override
        protected RxLoader<GeraltWomanPhotoCursor> getLoader(final int id, final RxLoaderArguments args) {
            return loaderFactory.create(args.getLong(ARG_ID));
        }

        @Override
        protected void onSuccess(final int id, final GeraltWomanPhotoCursor data) {
            getViewState().showData(data);
        }

        @Override
        protected void onError(final int id, final Throwable error) {
            getViewState().showError(error.getMessage());
        }
    }
}
