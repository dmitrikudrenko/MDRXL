package io.github.dmitrikudrenko.mdrxl.sample.ui;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.mvp.RxPresenter;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.Data;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.DataLoader;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.DataRepository;
import io.github.dmitrikudrenko.mdrxl.sample.model.settings.NetworkSettingsRepository;
import io.github.dmitrikudrenko.mdrxl.sample.model.settings.Settings;
import io.github.dmitrikudrenko.mdrxl.sample.model.settings.SettingsLoader;

import javax.inject.Inject;
import javax.inject.Provider;

@InjectViewState
public class SamplePresenter extends RxPresenter<SampleView> {
    private static final int LOADER_ID_DATA = 0;
    private static final int LOADER_ID_SETTINGS = 1;

    private final Provider<DataLoader> dataLoaderProvider;
    private final Provider<SettingsLoader> settingsLoaderProvider;

    private final DataRepository dataRepository;
    private final NetworkSettingsRepository networkSettingsRepository;

    @Inject
    SamplePresenter(final RxLoaderManager loaderManager,
                    final Provider<DataLoader> dataLoaderProvider,
                    final Provider<SettingsLoader> settingsLoaderProvider,
                    final DataRepository dataRepository,
                    final NetworkSettingsRepository networkSettingsRepository) {
        super(loaderManager);
        this.dataLoaderProvider = dataLoaderProvider;
        this.settingsLoaderProvider = settingsLoaderProvider;
        this.dataRepository = dataRepository;
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
        dataRepository.save(Data.create(number));
    }

    void onSuccessSet() {
        networkSettingsRepository.set(NetworkSettingsRepository.NetworkPreference.KEY_SUCCESS, true);
        networkSettingsRepository.set(NetworkSettingsRepository.NetworkPreference.KEY_TIMEOUT, false);
        networkSettingsRepository.set(NetworkSettingsRepository.NetworkPreference.KEY_ERROR, false);
    }

    void onTimeoutSet() {
        networkSettingsRepository.set(NetworkSettingsRepository.NetworkPreference.KEY_SUCCESS, false);
        networkSettingsRepository.set(NetworkSettingsRepository.NetworkPreference.KEY_TIMEOUT, true);
        networkSettingsRepository.set(NetworkSettingsRepository.NetworkPreference.KEY_ERROR, false);
    }

    void onErrorSet() {
        networkSettingsRepository.set(NetworkSettingsRepository.NetworkPreference.KEY_SUCCESS, false);
        networkSettingsRepository.set(NetworkSettingsRepository.NetworkPreference.KEY_TIMEOUT, false);
        networkSettingsRepository.set(NetworkSettingsRepository.NetworkPreference.KEY_ERROR, true);
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
