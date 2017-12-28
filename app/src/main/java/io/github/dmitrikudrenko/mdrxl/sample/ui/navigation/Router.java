package io.github.dmitrikudrenko.mdrxl.sample.ui.navigation;

import io.github.dmitrikudrenko.mdrxl.mvp.RxActivity;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.SampleApplication;
import io.github.dmitrikudrenko.mdrxl.sample.di.MultiWindow;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.details.GeraltWomanActivity;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.details.GeraltWomanFragment;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.photos.GeraltWomanPhotosActivity;

import javax.inject.Inject;

public class Router {
    private static final String TAG_DETAILS_FRAGMENT = "details_fragment";

    private final RxActivity activity;
    private final boolean multiWindow;

    @Inject
    Router(final RxActivity activity,
           @MultiWindow final boolean multiWindow) {
        this.activity = activity;
        this.multiWindow = multiWindow;
    }

    public void openGeraltWoman(final long id) {
        SampleApplication.createWomanComponent(id);
        if (multiWindow) {
            final GeraltWomanFragment geraltWomanFragment = GeraltWomanFragment.create();
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details, geraltWomanFragment, TAG_DETAILS_FRAGMENT)
                    .commitNow();
        } else {
            activity.startActivity(GeraltWomanActivity.intent(activity));
        }
    }

    public void openGeraltWomanPhotos() {
        activity.startActivity(GeraltWomanPhotosActivity.intent(activity));
    }
}
