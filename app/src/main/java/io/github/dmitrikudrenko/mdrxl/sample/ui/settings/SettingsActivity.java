package io.github.dmitrikudrenko.mdrxl.sample.ui.settings;

import android.os.Bundle;
import dagger.Provides;
import dagger.Subcomponent;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.mvp.RxActivity;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.SampleApplication;

import javax.inject.Inject;
import javax.inject.Provider;

public class SettingsActivity extends RxActivity {
    @Inject
    Provider<SettingsPresenter> settingsPresenterProvider;

    SettingsPresenter settingsPresenter;

    public SettingsPresenter getSettingsPresenter() {
        if (settingsPresenter == null) {
            settingsPresenter = settingsPresenterProvider.get();
        }
        return settingsPresenter;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_settings);
        final SettingsViewGroup settingsViewGroup = new SettingsViewGroup(findViewById(R.id.group_network_settings));
        settingsViewGroup.attachPresenter(getSettingsPresenter());
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
