package io.github.dmitrikudrenko.mdrxl.sample.ui.women.list;

import com.arellomobile.mvp.InjectViewState;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderArguments;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaders;
import io.github.dmitrikudrenko.mdrxl.mvp.RxPresenter;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomenCursor;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomenLoader;

import javax.inject.Inject;
import javax.inject.Provider;

@InjectViewState
public class GeraltWomenPresenter extends RxPresenter<GeraltWomenView> {
    private static final int LOADER_ID = RxLoaders.generateId();

    private final Provider<GeraltWomenLoader> loaderProvider;

    private GeraltWomenLoader loader;

    @Inject
    GeraltWomenPresenter(final RxLoaderManager loaderManager,
                         final Provider<GeraltWomenLoader> loaderProvider) {
        super(loaderManager);
        this.loaderProvider = loaderProvider;
    }

    @Override
    public void attachView(final GeraltWomenView view) {
        super.attachView(view);
        loader = (GeraltWomenLoader) getLoaderManager().init(LOADER_ID, null, new LoaderCallbacks());
    }

    void onRefresh() {
        //???
        getViewState().stopLoading();
    }

    void onItemSelected(final long id) {
        getViewState().openDataDetails(id);
    }

    void onSearchQuerySubmitted(final String query) {
        loader.setSearchQuery(query);
    }

    void onSearchQueryChanged(final String query) {
        //do nothing
    }

    void onSearchClosed() {
        loader.flushSearch();
    }

    private class LoaderCallbacks extends RxLoaderCallbacks<GeraltWomenCursor> {
        @Override
        protected RxLoader<GeraltWomenCursor> getLoader(final int id, final RxLoaderArguments args) {
            getViewState().startLoading();
            return loaderProvider.get();
        }

        @Override
        protected void onSuccess(final int id, final GeraltWomenCursor data) {
            getViewState().stopLoading();
            getViewState().showData(data);
        }

        @Override
        protected void onError(final int id, final Throwable error) {
            getViewState().showError(error.getMessage());
        }
    }
}
