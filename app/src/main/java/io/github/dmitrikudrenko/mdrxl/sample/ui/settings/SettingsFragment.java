package io.github.dmitrikudrenko.mdrxl.sample.ui.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import dagger.Provides;
import dagger.Subcomponent;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.mvp.RxFragment;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.SampleApplication;

import javax.inject.Inject;
import javax.inject.Provider;

public class SettingsFragment extends RxFragment implements SettingsView {

    @Inject
    Provider<SettingsPresenter> settingsPresenterProvider;

    @InjectPresenter
    SettingsPresenter settingsPresenter;

    private CompoundButton successButton;
    private CompoundButton timeoutButton;
    private CompoundButton errorButton;

    @ProvidePresenter
    public SettingsPresenter providePresenter() {
        if (settingsPresenter == null) {
            settingsPresenter = settingsPresenterProvider.get();
        }
        return settingsPresenter;
    }

    @Override
    protected void beforeOnCreate(final Bundle savedInstanceState) {
        SampleApplication.get().plus(new Module()).inject(this);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsPresenter = (SettingsPresenter) getLastCustomNonConfigurationInstance();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_settings, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        successButton = view.findViewById(R.id.button_network_success);
        timeoutButton = view.findViewById(R.id.button_network_timeout);
        errorButton = view.findViewById(R.id.button_network_error);

        final RadioGroup settingsGroup = view.findViewById(R.id.group_network_settings);
        settingsGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == successButton.getId()) {
                settingsPresenter.onSuccessSet();
            } else if (checkedId == timeoutButton.getId()) {
                settingsPresenter.onTimeoutSet();
            } else if (checkedId == errorButton.getId()) {
                settingsPresenter.onErrorSet();
            }
        });
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return settingsPresenter;
    }

    @Override
    public void showSuccessSetting(final boolean value) {
        successButton.setChecked(value);
    }

    @Override
    public void showTimeoutSetting(final boolean value) {
        timeoutButton.setChecked(value);
    }

    @Override
    public void showErrorSetting(final boolean value) {
        errorButton.setChecked(value);
    }

    @dagger.Module
    public class Module {
        @Provides
        RxLoaderManager provideLoaderManager() {
            return new RxLoaderManager(getLoaderManager());
        }
    }

    @Subcomponent(modules = Module.class)
    public interface Component {
        void inject(SettingsFragment fragment);
    }
}
