package io.github.dmitrikudrenko.sample.ui;

import android.support.annotation.IntDef;
import com.arellomobile.mvp.InjectViewState;
import io.github.dmitrikudrenko.mdrxl.mvp.RxPresenter;
import io.github.dmitrikudrenko.sample.ui.navigation.Router;

import javax.inject.Inject;

import static io.github.dmitrikudrenko.sample.ui.RootPresenter.Section.*;

@InjectViewState
public class RootPresenter extends RxPresenter<RootView> {
    private static final int DEFAULT_SECTION = WOMEN;

    @IntDef({WOMEN, VIDEO, SETTINGS})
    @interface Section {
        int WOMEN = 1;
        int VIDEO = 2;
        int SETTINGS = 3;
    }

    private final Router router;

    @Section
    private int section;

    @Inject
    public RootPresenter(final Router router) {
        this.router = router;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        onWomenClicked();
    }

    void onWomenClicked() {
        if (checkAndSet(WOMEN)) {
            router.openGeraltWomen();
        }
    }

    void onSettingsClicked() {
        if (checkAndSet(SETTINGS)) {
            router.openSettings();
        }
    }

    void onVideoClicked() {
        if (checkAndSet(VIDEO)) {
            router.openWitcherVideos();
        }
    }

    private boolean checkAndSet(@Section final int section) {
        if (this.section != section) {
            this.section = section;
            return true;
        }
        return false;
    }

    void onBackPressed() {
        if (section != DEFAULT_SECTION) {
            getViewState().setWomenSectionSelected();
        } else {
            getViewState().close();
        }
    }
}
