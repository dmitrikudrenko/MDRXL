package io.github.dmitrikudrenko.mdrxl.sample.ui.women.list;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import io.github.dmitrikudrenko.mdrxl.sample.ui.base.LoadingRxView;

@StateStrategyType(SkipStrategy.class)
public interface GeraltWomenView extends LoadingRxView {
    void showError(String message);

    void notifyDataChanged();

    void openDataDetails(long id);
}
