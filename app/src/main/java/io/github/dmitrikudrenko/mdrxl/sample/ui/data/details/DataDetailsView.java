package io.github.dmitrikudrenko.mdrxl.sample.ui.data.details;

import android.support.annotation.StringDef;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import io.github.dmitrikudrenko.mdrxl.mvp.RxView;
import io.github.dmitrikudrenko.mdrxl.mvp.SingleExecutionStateStrategy;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.Data;

import static io.github.dmitrikudrenko.mdrxl.sample.ui.data.details.DataDetailsView.Fields.*;

@StateStrategyType(SkipStrategy.class)
public interface DataDetailsView extends RxView {
    @StringDef({NAME, FIRST_ATTRIBUTE, SECOND_ATTRIBUTE, THIRD_ATTRIBUTE})
    @interface Fields {
        String NAME = Data.Fields.NAME;
        String FIRST_ATTRIBUTE = Data.Fields.FIRST_ATTRIBUTE;
        String SECOND_ATTRIBUTE = Data.Fields.SECOND_ATTRIBUTE;
        String THIRD_ATTRIBUTE = Data.Fields.THIRD_ATTRIBUTE;
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
}
