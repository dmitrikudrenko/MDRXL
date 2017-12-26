package io.github.dmitrikudrenko.mdrxl.sample.ui.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
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

    @BindView(R.id.button_network_success)
    CompoundButton successButton;
    @BindView(R.id.button_network_timeout)
    CompoundButton timeoutButton;
    @BindView(R.id.button_network_error)
    CompoundButton errorButton;

    private MuteableOnCheckedChangeListener onSettingsChangeListener;

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
        if (settingsPresenter == null) {
            settingsPresenter = (SettingsPresenter) getLastCustomNonConfigurationInstance();
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_settings, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        final RadioGroup settingsGroup = view.findViewById(R.id.group_network_settings);
        onSettingsChangeListener = new MuteableOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == successButton.getId()) {
                settingsPresenter.onSuccessSet();
            } else if (checkedId == timeoutButton.getId()) {
                settingsPresenter.onTimeoutSet();
            } else if (checkedId == errorButton.getId()) {
                settingsPresenter.onErrorSet();
            }
        });
        settingsGroup.setOnCheckedChangeListener(onSettingsChangeListener);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return settingsPresenter;
    }

    private void setButtonChecked(final CompoundButton compoundButton, final boolean value) {
        if (compoundButton.isChecked() != value) {
            onSettingsChangeListener.mute();
            compoundButton.setChecked(value);
            compoundButton.jumpDrawablesToCurrentState();
            onSettingsChangeListener.unmute();
        }
    }

    @Override
    public void showSuccessSetting(final boolean value) {
        setButtonChecked(successButton, value);
    }

    @Override
    public void showTimeoutSetting(final boolean value) {
        setButtonChecked(timeoutButton, value);
    }

    @Override
    public void showErrorSetting(final boolean value) {
        setButtonChecked(errorButton, value);
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

    private class MuteableOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        private final RadioGroup.OnCheckedChangeListener onCheckedChangeListener;
        private boolean mute;

        MuteableOnCheckedChangeListener(final RadioGroup.OnCheckedChangeListener onCheckedChangeListener) {
            this.onCheckedChangeListener = onCheckedChangeListener;
        }

        @Override
        public void onCheckedChanged(final RadioGroup group, final int checkedId) {
            if (!mute) {
                onCheckedChangeListener.onCheckedChanged(group, checkedId);
            }
        }

        void mute() {
            this.mute = true;
        }

        void unmute() {
            this.mute = false;
        }
    }
}
