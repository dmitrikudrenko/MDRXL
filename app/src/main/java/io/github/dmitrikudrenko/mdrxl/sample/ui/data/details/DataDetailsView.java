package io.github.dmitrikudrenko.mdrxl.sample.ui.data.details;

import android.support.annotation.StringDef;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.Data;
import io.github.dmitrikudrenko.mdrxl.sample.ui.base.LoadingRxView;

import static io.github.dmitrikudrenko.mdrxl.sample.ui.data.details.DataDetailsView.Fields.*;

@StateStrategyType(SkipStrategy.class)
public interface DataDetailsView extends LoadingRxView {
    @StringDef({NAME, FIRST_ATTRIBUTE, SECOND_ATTRIBUTE, THIRD_ATTRIBUTE})
    @interface Fields {
        String NAME = Data.Fields.NAME;
        String FIRST_ATTRIBUTE = Data.Fields.FIRST_ATTRIBUTE;
        String SECOND_ATTRIBUTE = Data.Fields.SECOND_ATTRIBUTE;
        String THIRD_ATTRIBUTE = Data.Fields.THIRD_ATTRIBUTE;
    }

    void showId(String value);

    void showName(String value);

    void showFirstAttribute(String value);

    void showSecondAttribute(String value);

    void showThirdAttribute(String value);

    void showError(String error);

    void showMessage(String message);
}
