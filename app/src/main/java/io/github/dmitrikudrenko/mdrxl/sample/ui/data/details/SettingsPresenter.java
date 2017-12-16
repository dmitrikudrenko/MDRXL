package io.github.dmitrikudrenko.mdrxl.sample.ui.data.details;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaders;
import io.github.dmitrikudrenko.mdrxl.mvp.RxPresenter;
import io.github.dmitrikudrenko.mdrxl.sample.settings.NetworkSettingsRepository;
import io.github.dmitrikudrenko.mdrxl.sample.settings.Settings;
import io.github.dmitrikudrenko.mdrxl.sample.settings.SettingsLoader;

import javax.inject.Inject;
import javax.inject.Provider;

@InjectViewState
public class SettingsPresenter extends RxPresenter<SettingsView> {
    private static final int LOADER_ID_SETTINGS = RxLoaders.generateId();

    private final NetworkSettingsRepository networkSettingsRepository;
    private final Provider<SettingsLoader> settingsLoaderProvider;

    @Inject
    SettingsPresenter(final RxLoaderManager loaderManager,
                      final NetworkSettingsRepository networkSettingsRepository,
                      final Provider<SettingsLoader> settingsLoaderProvider) {
        super(loaderManager);
        this.networkSettingsRepository = networkSettingsRepository;
        this.settingsLoaderProvider = settingsLoaderProvider;
    }

    @Override
    public void attachView(final SettingsView view) {
        super.attachView(view);
        getLoaderManager().init(LOADER_ID_SETTINGS, null, new SettingsLoaderCallback());
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
