package io.github.dmitrikudrenko.mdrxl.sample.ui.data.details;

import com.arellomobile.mvp.InjectViewState;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaders;
import io.github.dmitrikudrenko.mdrxl.mvp.RxPresenter;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.Data;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.DataStorageCommand;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.UpdateModel;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.local.DataLoader;
import rx.android.schedulers.AndroidSchedulers;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;

@InjectViewState
public class SamplePresenter extends RxPresenter<SampleView> {
    private static final int LOADER_ID_DATA = RxLoaders.generateId();

    private final Provider<DataLoader> dataLoaderProvider;
    private final Provider<DataStorageCommand> dataStorageCommandProvider;

    @Nullable
    private Data data;

    @Inject
    SamplePresenter(final RxLoaderManager loaderManager,
                    final Provider<DataLoader> dataLoaderProvider,
                    final Provider<DataStorageCommand> dataStorageCommandProvider) {
        super(loaderManager);
        this.dataLoaderProvider = dataLoaderProvider;
        this.dataStorageCommandProvider = dataStorageCommandProvider;
    }

    @Override
    public void attachView(final SampleView view) {
        super.attachView(view);
        getLoaderManager().init(LOADER_ID_DATA, null, new DataLoaderCallbacks());
    }

    void onRefresh() {
        //???
        getViewState().stopLoading();
        notifyViewState(data);
    }

    void onDataChanged(@SampleView.Fields final String field, final String value) {
        final DataStorageCommand dataStorageCommand = dataStorageCommandProvider.get();
        dataStorageCommand.save(createUpdateModel(field, value))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> getViewState().showMessage("Data updated"),
                        error -> {
                            getViewState().showError(error.getMessage());
                            getLoaderManager().getLoader(LOADER_ID_DATA).onContentChanged();
                        });
    }

    private UpdateModel createUpdateModel(final String field, final String value) {
        return UpdateModel.create(field, value);
    }

    private void onDataLoaded(final Data data) {
        this.data = data;
        notifyViewState(data);
    }

    private void notifyViewState(@Nullable final Data data) {
        if (data == null) {
            return;
        }

        getViewState().showId(String.valueOf(data.getId()));
        getViewState().showName(data.getName());
        getViewState().showFirstAttribute(data.getFirstAttribute());
        getViewState().showSecondAttribute(data.getSecondAttribute());
        getViewState().showThirdAttribute(data.getThirdAttribute());
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
            onDataLoaded(data);
        }

        @Override
        protected void onError(final int id, final Throwable error) {
            getViewState().stopLoading();
            getViewState().showError(error.getMessage());
        }

    }
}
