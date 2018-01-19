package io.github.dmitrikudrenko.sample.ui.settings;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import io.github.dmitrikudrenko.mdrxl.mvp.RxView;

@StateStrategyType(SkipStrategy.class)
public interface SettingsView extends RxView {
    void showSuccessSetting(boolean value);

    void showTimeoutSetting(boolean value);

    void showErrorSetting(boolean value);
}
