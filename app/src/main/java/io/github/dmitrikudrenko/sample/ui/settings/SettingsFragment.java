package io.github.dmitrikudrenko.sample.ui.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import butterknife.BindView;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import dagger.Provides;
import dagger.Subcomponent;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.sample.GeraltApplication;
import io.github.dmitrikudrenko.sample.R;
import io.github.dmitrikudrenko.sample.ui.base.BaseRxFragment;
import io.github.dmitrikudrenko.sample.utils.ui.MuteableOnCheckedChangeListener;

import javax.inject.Inject;

public class SettingsFragment extends BaseRxFragment implements SettingsView {

    @Inject
    @InjectPresenter
    SettingsPresenter presenter;

    @BindView(R.id.button_network_success)
    CompoundButton successButton;
    @BindView(R.id.button_network_timeout)
    CompoundButton timeoutButton;
    @BindView(R.id.button_network_error)
    CompoundButton errorButton;

    private MuteableOnCheckedChangeListener onSettingsChangeListener;

    @ProvidePresenter
    public SettingsPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected void beforeOnCreate(final Bundle savedInstanceState) {
        GeraltApplication.get().plus(new Module()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_settings, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RadioGroup settingsGroup = view.findViewById(R.id.group_network_settings);
        onSettingsChangeListener = new MuteableOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == successButton.getId()) {
                presenter.onSuccessSet();
            } else if (checkedId == timeoutButton.getId()) {
                presenter.onTimeoutSet();
            } else if (checkedId == errorButton.getId()) {
                presenter.onErrorSet();
            }
        });
        settingsGroup.setOnCheckedChangeListener(onSettingsChangeListener);
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
}
