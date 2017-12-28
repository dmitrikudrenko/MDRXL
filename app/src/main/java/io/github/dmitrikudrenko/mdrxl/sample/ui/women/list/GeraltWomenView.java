package io.github.dmitrikudrenko.mdrxl.sample.ui.women.list;

import android.support.v7.util.DiffUtil;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import io.github.dmitrikudrenko.mdrxl.sample.ui.base.LoadingRxView;

@StateStrategyType(SkipStrategy.class)
public interface GeraltWomenView extends LoadingRxView {
    void showError(String message);

    void notifyDataChanged(int position);

    void notifyDataChanged(DiffUtil.DiffResult result);

    void openDataDetails(long id);

    void showSearchQuery(String value);
}
