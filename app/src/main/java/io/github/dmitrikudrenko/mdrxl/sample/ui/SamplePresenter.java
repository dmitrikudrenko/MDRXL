package io.github.dmitrikudrenko.mdrxl.sample.ui;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.mvp.RxPresenter;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.Data;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.DataStorageCommand;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.local.DataLoader;
import io.github.dmitrikudrenko.mdrxl.sample.model.settings.NetworkSettingsRepository;
import io.github.dmitrikudrenko.mdrxl.sample.model.settings.Settings;
import io.github.dmitrikudrenko.mdrxl.sample.model.settings.SettingsLoader;
import rx.android.schedulers.AndroidSchedulers;

import javax.inject.Inject;
import javax.inject.Provider;

@InjectViewState
public class SamplePresenter extends RxPresenter<SampleView> {
    private static final int LOADER_ID_DATA = 0;
    private static final int LOADER_ID_SETTINGS = 1;

    private final Provider<DataLoader> dataLoaderProvider;
    private final Provider<SettingsLoader> settingsLoaderProvider;

    private final Provider<DataStorageCommand> dataStorageCommandProvider;
    private final NetworkSettingsRepository networkSettingsRepository;

    @Inject
    SamplePresenter(final RxLoaderManager loaderManager,
                    final Provider<DataLoader> dataLoaderProvider,
                    final Provider<SettingsLoader> settingsLoaderProvider,
                    final Provider<DataStorageCommand> dataStorageCommandProvider,
                    final NetworkSettingsRepository networkSettingsRepository) {
        super(loaderManager);
        this.dataLoaderProvider = dataLoaderProvider;
        this.settingsLoaderProvider = settingsLoaderProvider;
        this.dataStorageCommandProvider = dataStorageCommandProvider;
        this.networkSettingsRepository = networkSettingsRepository;
    }

    @Override
    public void attachView(final SampleView view) {
        super.attachView(view);
        getLoaderManager().init(LOADER_ID_DATA, null, new DataLoaderCallbacks());
        getLoaderManager().init(LOADER_ID_SETTINGS, null, new SettingsLoaderCallback());
    }

    void onRefresh() {
        //???
        getViewState().stopLoading();
    }

    void onDataChanged(final String data) {
        final int number = Integer.valueOf(data);
        final DataStorageCommand dataStorageCommand = dataStorageCommandProvider.get();
        dataStorageCommand.save(Data.create(number, ""))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(error -> {
                    getViewState().showError(error.getMessage());
                    getLoaderManager().getLoader(LOADER_ID_DATA).onContentChanged();
                }, () -> getViewState().showMessage("Data updated"));
    }

    void onSuccessSet() {
        networkSettingsRepository.set(NetworkSettingsRepository.NetworkPreference.KEY_SUCCESS);
    }

    void onTimeoutSet() {
        networkSettingsRepository.set(NetworkSettingsRepository.NetworkPreference.KEY_TIMEOUT);
    }

    void onErrorSet() {
        networkSettingsRepository.set(NetworkSettingsRepository.NetworkPreference.KEY_ERROR);
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
            getViewState().showData(String.valueOf(data.getId()));
        }

        @Override
        protected void onError(final int id, final Throwable error) {
            getViewState().stopLoading();
            getViewState().showError(error.getMessage());
        }
    }

    private class SettingsLoaderCallback extends RxLoaderCallbacks<Settings> {

        @Override
        protected RxLoader<Settings> getLoader(final int id) {
            return settingsLoaderProvider.get();
        }

        @Override
        protected void onSuccess(final int id, final Settings data) {
            getViewState().showSuccessSetting(data.isSuccess());
            getViewState().showTimeoutSetting(data.isTimeout());
            getViewState().showErrorSetting(data.isError());
        }

        @Override
        protected void onError(final int id, final Throwable error) {
            Log.e("SettingsLoaderCallback", error.getMessage(), error);
        }
    }
}
