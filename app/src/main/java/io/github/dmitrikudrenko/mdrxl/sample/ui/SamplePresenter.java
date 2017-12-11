package io.github.dmitrikudrenko.mdrxl.sample.ui;

import com.arellomobile.mvp.InjectViewState;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.mvp.RxPresenter;
import io.github.dmitrikudrenko.mdrxl.sample.model.Data;
import io.github.dmitrikudrenko.mdrxl.sample.model.DataLoader;

import javax.inject.Inject;
import javax.inject.Provider;

@InjectViewState
public class SamplePresenter extends RxPresenter<SampleView> {
    private final Provider<DataLoader> dataLoaderProvider;

    @Inject
    SamplePresenter(final RxLoaderManager loaderManager,
                    final Provider<DataLoader> dataLoaderProvider) {
        super(loaderManager);
        this.dataLoaderProvider = dataLoaderProvider;
    }

    @Override
    public void attachView(final SampleView view) {
        super.attachView(view);
        getLoaderManager().init(0, null, new DataLoaderCallbacks());
    }

    void onRefresh() {
        //???
        getViewState().stopLoading();
    }

    private class DataLoaderCallbacks extends RxLoaderCallbacks<Data> {

        @Override
        protected RxLoader<Data> getLoader(final int id) {
            getViewState().startLoading();
            return dataLoaderProvider.get();
        }

        @Override
        protected void onSuccess(final int id, final Data data) {
            getViewState().stopLoading();
            getViewState().showData(data);
        }

        @Override
        protected void onError(final int id, final Throwable error) {
            getViewState().stopLoading();
            getViewState().showError(error);
        }
    }
}
