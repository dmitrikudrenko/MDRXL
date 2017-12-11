package io.github.dmitrikudrenko.mdrxl.sample.ui;

import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import io.github.dmitrikudrenko.mdrxl.mvp.RxView;
import io.github.dmitrikudrenko.mdrxl.mvp.SingleExecutionStateStrategy;
import io.github.dmitrikudrenko.mdrxl.sample.model.Data;

@StateStrategyType(SkipStrategy.class)
public interface SampleView extends RxView {
    @StateStrategyType(SingleStateStrategy.class)
    void startLoading();

    @StateStrategyType(SingleExecutionStateStrategy.class)
    void stopLoading();

    void showData(Data data);

    void showError(Throwable error);
}
