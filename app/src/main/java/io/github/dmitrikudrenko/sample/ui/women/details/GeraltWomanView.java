package io.github.dmitrikudrenko.sample.ui.women.details;

import android.support.annotation.StringDef;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import io.github.dmitrikudrenko.sample.ui.base.LoadingRxView;

import static io.github.dmitrikudrenko.sample.ui.women.details.GeraltWomanView.Fields.*;

@StateStrategyType(SkipStrategy.class)
public interface GeraltWomanView extends LoadingRxView {
    @StringDef({NAME, PHOTO, PROFESSION, HAIR_COLOR})
    @interface Fields {

        String NAME = "name";
        String PHOTO = "photo";
        String PROFESSION = "profession";
        String HAIR_COLOR = "hair_color";
    }
    void showName(String value);

    void showPhoto(String value);

    void showProfession(String value);

    void showHairColor(String value);
}
