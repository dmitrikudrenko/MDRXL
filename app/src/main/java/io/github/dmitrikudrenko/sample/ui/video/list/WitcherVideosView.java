package io.github.dmitrikudrenko.sample.ui.video.list;

import android.support.v7.util.DiffUtil;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import io.github.dmitrikudrenko.sample.ui.base.LoadingRxView;

@StateStrategyType(SkipStrategy.class)
public interface WitcherVideosView extends LoadingRxView {
    void notifyDataSetChanged(DiffUtil.DiffResult result);

    void notifyDataChanged(int position);

    void showCastingDialog(String thumbnail);
}
