package io.github.dmitrikudrenko.mdrxl.sample.ui.data.list;

import com.arellomobile.mvp.InjectViewState;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderArguments;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaders;
import io.github.dmitrikudrenko.mdrxl.mvp.RxPresenter;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.local.DataCursor;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.local.DataListLoader;

import javax.inject.Inject;
import javax.inject.Provider;

@InjectViewState
public class DataListPresenter extends RxPresenter<DataListView> {
    private static final int LOADER_ID = RxLoaders.generateId();

    private final Provider<DataListLoader> loaderProvider;

    private DataListLoader loader;

    @Inject
    DataListPresenter(final RxLoaderManager loaderManager,
                      final Provider<DataListLoader> loaderProvider) {
        super(loaderManager);
        this.loaderProvider = loaderProvider;
    }

    @Override
    public void attachView(final DataListView view) {
        super.attachView(view);
        loader = (DataListLoader) getLoaderManager().init(LOADER_ID, null, new DataListLoaderCallbacks());
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

    private class DataListLoaderCallbacks extends RxLoaderCallbacks<DataCursor> {
        @Override
        protected RxLoader<DataCursor> getLoader(final int id, final RxLoaderArguments args) {
            getViewState().startLoading();
            return loaderProvider.get();
        }

        @Override
        protected void onSuccess(final int id, final DataCursor data) {
            getViewState().stopLoading();
            getViewState().showData(data);
        }

        @Override
        protected void onError(final int id, final Throwable error) {
            getViewState().showError(error.getMessage());
        }
    }
}
