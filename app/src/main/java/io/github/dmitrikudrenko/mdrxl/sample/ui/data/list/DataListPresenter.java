package io.github.dmitrikudrenko.mdrxl.sample.ui.data.list;

import com.arellomobile.mvp.InjectViewState;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
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

    @Inject
    DataListPresenter(final RxLoaderManager loaderManager,
                      final Provider<DataListLoader> loaderProvider) {
        super(loaderManager);
        this.loaderProvider = loaderProvider;
    }

    @Override
    public void attachView(final DataListView view) {
        super.attachView(view);
        getLoaderManager().init(LOADER_ID, null, new DataListLoaderCallbacks());
    }

    void onRefresh() {
        //???
        getViewState().stopLoading();
    }

    private class DataListLoaderCallbacks extends RxLoaderCallbacks<DataCursor> {
        @Override
        protected RxLoader<DataCursor> getLoader(final int id) {
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
