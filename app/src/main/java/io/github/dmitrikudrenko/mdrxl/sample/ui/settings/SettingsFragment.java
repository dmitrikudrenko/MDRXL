package io.github.dmitrikudrenko.mdrxl.sample.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import io.github.dmitrikudrenko.mdrxl.mvp.RxFragment;
import io.github.dmitrikudrenko.mdrxl.sample.R;

public class SettingsFragment extends RxFragment implements SettingsView {

    private SettingsController settingsController;

    private CompoundButton successButton;
    private CompoundButton timeoutButton;
    private CompoundButton errorButton;

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (context instanceof SettingsController) {
            settingsController = (SettingsController) context;
        } else {
            throw new IllegalArgumentException("Context should implement SettingsController");
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
        successButton = view.findViewById(R.id.button_network_success);
        timeoutButton = view.findViewById(R.id.button_network_timeout);
        errorButton = view.findViewById(R.id.button_network_error);

        final RadioGroup settingsGroup = view.findViewById(R.id.group_network_settings);
        settingsGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == successButton.getId()) {
                settingsController.onSuccessSet();
            } else if (checkedId == timeoutButton.getId()) {
                settingsController.onTimeoutSet();
            } else if (checkedId == errorButton.getId()) {
                settingsController.onErrorSet();
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

    public interface SettingsController {
        void onSuccessSet();

        void onTimeoutSet();

        void onErrorSet();
    }
}
