package io.github.dmitrikudrenko.mdrxl.sample.ui.women.list;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomenCursor;
import io.github.dmitrikudrenko.mdrxl.sample.ui.base.LoadingRxView;

@StateStrategyType(SkipStrategy.class)
public interface GeraltWomenView extends LoadingRxView {
    void showError(String message);

    void showData(GeraltWomenCursor cursor);

    void openDataDetails(long id);
}
