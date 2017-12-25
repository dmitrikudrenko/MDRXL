package io.github.dmitrikudrenko.mdrxl.sample.ui.women.details;

import android.support.annotation.StringDef;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.GeraltWoman;
import io.github.dmitrikudrenko.mdrxl.sample.ui.base.LoadingRxView;

import static io.github.dmitrikudrenko.mdrxl.sample.ui.women.details.GeraltWomanView.Fields.*;

@StateStrategyType(SkipStrategy.class)
public interface GeraltWomanView extends LoadingRxView {
    @StringDef({NAME, PHOTO, PROFESSION, HAIR_COLOR})
    @interface Fields {
        String NAME = GeraltWoman.Fields.NAME;
        String PHOTO = GeraltWoman.Fields.PHOTO;
        String PROFESSION = GeraltWoman.Fields.PROFESSION;
        String HAIR_COLOR = GeraltWoman.Fields.HAIR_COLOR;
    }

    void showName(String value);

    void showPhoto(String value);

    void showProfession(String value);

    void showHairColor(String value);

    void showError(String error);

    void showMessage(String message);
}
