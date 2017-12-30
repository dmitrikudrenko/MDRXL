package io.github.dmitrikudrenko.sample.ui;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import io.github.dmitrikudrenko.mdrxl.mvp.RxView;

@StateStrategyType(SkipStrategy.class)
public interface RootView extends RxView {
    void close();

    void setWomenSectionSelected();
}
