package io.github.dmitrikudrenko.sample.ui.base;

import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import io.github.dmitrikudrenko.mdrxl.mvp.RxView;
import io.github.dmitrikudrenko.mdrxl.mvp.SingleExecutionStateStrategy;

public interface LoadingRxView extends RxView {
    @StateStrategyType(SingleStateStrategy.class)
    void startLoading();

    @StateStrategyType(SingleExecutionStateStrategy.class)
    void stopLoading();
}
