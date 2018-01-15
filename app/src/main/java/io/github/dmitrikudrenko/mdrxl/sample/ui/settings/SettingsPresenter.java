package io.github.dmitrikudrenko.mdrxl.sample.ui.settings;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import io.github.dmitrikudrenko.core.remote.settings.NetworkSettingsRepository;
import io.github.dmitrikudrenko.core.remote.settings.Settings;
import io.github.dmitrikudrenko.core.remote.settings.SettingsLoader;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderArguments;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaders;
import io.github.dmitrikudrenko.mdrxl.mvp.RxLoaderPresenter;

import javax.inject.Inject;
import javax.inject.Provider;

@InjectViewState
public class SettingsPresenter extends RxLoaderPresenter<SettingsView> {
    private static final int LOADER_ID = RxLoaders.generateId();

    private final NetworkSettingsRepository networkSettingsRepository;
    private final Provider<SettingsLoader> loaderProvider;

    @Inject
    SettingsPresenter(final RxLoaderManager loaderManager,
                      final NetworkSettingsRepository networkSettingsRepository,
                      final Provider<SettingsLoader> loaderProvider) {
        super(loaderManager);
        this.networkSettingsRepository = networkSettingsRepository;
        this.loaderProvider = loaderProvider;
    }

    @Override
    public void attachView(final SettingsView view) {
        super.attachView(view);
        getLoaderManager().init(LOADER_ID, null, new SettingsLoaderCallback());
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
        protected RxLoader<Settings> getLoader(final int id, final RxLoaderArguments args) {
            return loaderProvider.get();
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
