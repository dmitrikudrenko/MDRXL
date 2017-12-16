package io.github.dmitrikudrenko.mdrxl.sample.ui.data.details;

import com.arellomobile.mvp.InjectViewState;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderArguments;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaders;
import io.github.dmitrikudrenko.mdrxl.mvp.RxPresenter;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.DataStorageCommand;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.UpdateModel;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.local.DataCursor;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.local.DataLoader;
import rx.android.schedulers.AndroidSchedulers;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;

@InjectViewState
public class DataDetailsPresenter extends RxPresenter<DataDetailsView> {
    private static final int LOADER_ID_DATA = RxLoaders.generateId();

    private final Provider<DataLoader> dataLoaderProvider;
    private final Provider<DataStorageCommand> dataStorageCommandProvider;

    @Nullable
    private DataCursor data;

    private long id;

    @Inject
    DataDetailsPresenter(final RxLoaderManager loaderManager,
                         final Provider<DataLoader> dataLoaderProvider,
                         final Provider<DataStorageCommand> dataStorageCommandProvider) {
        super(loaderManager);
        this.dataLoaderProvider = dataLoaderProvider;
        this.dataStorageCommandProvider = dataStorageCommandProvider;
    }

    @Override
    public void attachView(final DataDetailsView view) {
        super.attachView(view);
        final RxLoaderArguments args = new RxLoaderArguments();
        args.putLong("id", id);
        getLoaderManager().init(LOADER_ID_DATA, args, new DataLoaderCallbacks());
    }

    void onRefresh() {
        //???
        getViewState().stopLoading();
        notifyViewState(data);
    }

    void onDataChanged(@DataDetailsView.Fields final String field, final String value) {
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

    private void onDataLoaded(final DataCursor data) {
        this.data = data;
        notifyViewState(data);
    }

    private void notifyViewState(@Nullable final DataCursor data) {
        if (data == null) {
            return;
        }

        getViewState().showId(String.valueOf(data.getId()));
        getViewState().showName(data.getName());
        getViewState().showFirstAttribute(data.getFirstAttribute());
        getViewState().showSecondAttribute(data.getSecondAttribute());
        getViewState().showThirdAttribute(data.getThirdAttribute());
    }

    public void setId(final long id) {
        this.id = id;
    }

    private class DataLoaderCallbacks extends RxLoaderCallbacks<DataCursor> {

        @Override
        protected RxLoader<DataCursor> getLoader(final int id, final RxLoaderArguments args) {
            getViewState().startLoading();
            final DataLoader loader = dataLoaderProvider.get();
            loader.setId(args.getLong("id"));
            return loader;
        }

        @Override
        protected void onSuccess(final int id, final DataCursor data) {
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
