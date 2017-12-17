package io.github.dmitrikudrenko.mdrxl.sample.ui.settings;

import android.os.Bundle;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import dagger.Provides;
import dagger.Subcomponent;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.mvp.RxActivity;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.SampleApplication;

import javax.inject.Inject;
import javax.inject.Provider;

public class SettingsActivity extends RxActivity implements SettingsView, SettingsFragment.SettingsController {
    private static final String TAG_SETTINGS_FRAGMENT = "settings_fragment";

    @Inject
    Provider<SettingsPresenter> settingsPresenterProvider;

    @InjectPresenter
    SettingsPresenter settingsPresenter;

    private SettingsFragment fragment;

    @ProvidePresenter
    public SettingsPresenter providePresenter() {
        if (settingsPresenter == null) {
            settingsPresenter = settingsPresenterProvider.get();
        }
        return settingsPresenter;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_fragment_holder);
        if (savedInstanceState == null) {
            fragment = new SettingsFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, fragment, TAG_SETTINGS_FRAGMENT)
                    .commitNow();
        } else {
            fragment = (SettingsFragment) getSupportFragmentManager()
                    .findFragmentByTag(TAG_SETTINGS_FRAGMENT);
        }
    }

    @Override
    protected void beforeOnCreate(final Bundle savedInstanceState) {
        settingsPresenter = (SettingsPresenter) getLastCustomNonConfigurationInstance();
        SampleApplication.get().plus(new Module()).inject(this);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return settingsPresenter;
    }

    @Override
    public void showSuccessSetting(final boolean value) {
        fragment.showSuccessSetting(value);
    }

    @Override
    public void showTimeoutSetting(final boolean value) {
        fragment.showTimeoutSetting(value);
    }

    @Override
    public void showErrorSetting(final boolean value) {
        fragment.showErrorSetting(value);
    }

    @Override
    public void onSuccessSet() {
        settingsPresenter.onSuccessSet();
    }

    @Override
    public void onTimeoutSet() {
        settingsPresenter.onTimeoutSet();
    }

    @Override
    public void onErrorSet() {
        settingsPresenter.onErrorSet();
    }

    @dagger.Module
    public class Module {
        @Provides
        RxLoaderManager provideLoaderManager() {
            return new RxLoaderManager(getSupportLoaderManager());
        }
    }

    @Subcomponent(modules = Module.class)
    public interface Component {
        void inject(SettingsActivity activity);
    }
}
