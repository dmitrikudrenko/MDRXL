package io.github.dmitrikudrenko.mdrxl.sample.ui.women.photos;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import io.github.dmitrikudrenko.mdrxl.mvp.RxView;

@StateStrategyType(SkipStrategy.class)
public interface GeraltWomanPhotosView extends RxView {
    void notifyDataChanged();

    void showPages(String value);
}
