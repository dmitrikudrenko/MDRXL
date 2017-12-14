package io.github.dmitrikudrenko.mdrxl.sample.ui;

import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import io.github.dmitrikudrenko.mdrxl.mvp.RxView;
import io.github.dmitrikudrenko.mdrxl.mvp.SingleExecutionStateStrategy;

@StateStrategyType(SkipStrategy.class)
public interface SampleView extends RxView {
    @StateStrategyType(SingleStateStrategy.class)
    void startLoading();

    @StateStrategyType(SingleExecutionStateStrategy.class)
    void stopLoading();

    void showId(String value);

    void showName(String value);

    void showError(String error);

    void showMessage(String message);

    void showSuccessSetting(boolean value);

    void showTimeoutSetting(boolean value);

    void showErrorSetting(boolean value);
}
