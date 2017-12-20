package io.github.dmitrikudrenko.mdrxl.sample.ui.data.list;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.local.DataCursor;
import io.github.dmitrikudrenko.mdrxl.sample.ui.base.LoadingRxView;

@StateStrategyType(SkipStrategy.class)
public interface DataListView extends LoadingRxView {
    void showError(String message);

    void showData(DataCursor cursor);

    void openDataDetails(long id);
}
