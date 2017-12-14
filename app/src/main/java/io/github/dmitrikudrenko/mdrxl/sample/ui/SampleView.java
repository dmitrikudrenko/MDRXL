package io.github.dmitrikudrenko.mdrxl.sample.ui;

import android.support.annotation.StringDef;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import io.github.dmitrikudrenko.mdrxl.mvp.RxView;
import io.github.dmitrikudrenko.mdrxl.mvp.SingleExecutionStateStrategy;

import static io.github.dmitrikudrenko.mdrxl.sample.ui.SampleView.Fields.*;

@StateStrategyType(SkipStrategy.class)
public interface SampleView extends RxView {
    @StringDef({ID, NAME, FIRST_ATTRIBUTE, SECOND_ATTRIBUTE, THIRD_ATTRIBUTE})
    @interface Fields {
        String ID = "ID";
        String NAME = "NAME";
        String FIRST_ATTRIBUTE = "FIRST_ATTRIBUTE";
        String SECOND_ATTRIBUTE = "SECOND_ATTRIBUTE";
        String THIRD_ATTRIBUTE = "THIRD_ATTRIBUTE";
    }

    @StateStrategyType(SingleStateStrategy.class)
    void startLoading();

    @StateStrategyType(SingleExecutionStateStrategy.class)
    void stopLoading();

    void showId(String value);

    void showName(String value);

    void showFirstAttribute(String value);

    void showSecondAttribute(String value);

    void showThirdAttribute(String value);

    void showError(String error);

    void showMessage(String message);

    void showSuccessSetting(boolean value);

    void showTimeoutSetting(boolean value);

    void showErrorSetting(boolean value);
}
