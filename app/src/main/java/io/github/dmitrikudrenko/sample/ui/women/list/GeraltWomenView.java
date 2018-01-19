package io.github.dmitrikudrenko.sample.ui.women.list;

import android.support.v7.util.DiffUtil;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import io.github.dmitrikudrenko.sample.ui.base.LoadingRxView;

@StateStrategyType(SkipStrategy.class)
public interface GeraltWomenView extends LoadingRxView {
    void notifyDataChanged(int position);

    void notifyDataChanged(DiffUtil.DiffResult result);

    void showSearchQuery(String value);
}
