package io.github.dmitrikudrenko.mdrxl.sample.ui.data.details;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import io.github.dmitrikudrenko.mdrxl.mvp.MvpViewGroup;
import io.github.dmitrikudrenko.mdrxl.sample.R;

public class SettingsViewGroup extends MvpViewGroup<SettingsPresenter, SettingsView> implements SettingsView {
    private final CompoundButton successButton;
    private final CompoundButton timeoutButton;
    private final CompoundButton errorButton;

    SettingsViewGroup(final View view) {
        super(view);
        successButton = view.findViewById(R.id.button_network_success);
        timeoutButton = view.findViewById(R.id.button_network_timeout);
        errorButton = view.findViewById(R.id.button_network_error);

        final RadioGroup settingsGroup = view.findViewById(R.id.group_network_settings);
        settingsGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == successButton.getId()) {
                getPresenter().onSuccessSet();
            } else if (checkedId == timeoutButton.getId()) {
                getPresenter().onTimeoutSet();
            } else if (checkedId == errorButton.getId()) {
                getPresenter().onErrorSet();
            }
        });
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
}
