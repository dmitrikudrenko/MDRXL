package org.robolectric.shadows.support.v4;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.LinearLayout;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.android.controller.ComponentController;

public class SupportFragmentControllerExt<F extends Fragment> extends ComponentController<SupportFragmentControllerExt<F>, F> {
    private final F fragment;
    private final ActivityController<? extends FragmentActivity> activityController;

    private SupportFragmentControllerExt(final F fragment, final Class<? extends FragmentActivity> activityClass) {
        this(fragment, activityClass, null);
    }

    private SupportFragmentControllerExt(final F fragment, final Class<? extends FragmentActivity> activityClass, final Intent intent) {
        super(Robolectric.getShadowsAdapter(), fragment, intent);
        this.fragment = fragment;
        this.activityController = Robolectric.buildActivity(activityClass, intent);
    }

    public static <F extends Fragment> SupportFragmentControllerExt<F> of(final F fragment) {
        return new SupportFragmentControllerExt<>(fragment, FragmentControllerActivity.class);
    }

    public static <F extends Fragment> SupportFragmentControllerExt<F> of(final F fragment, final Class<? extends FragmentActivity> activityClass) {
        return new SupportFragmentControllerExt<>(fragment, activityClass);
    }

    public static <F extends Fragment> SupportFragmentControllerExt<F> of(final F fragment, final Class<? extends FragmentActivity> activityClass, final Intent intent) {
        return new SupportFragmentControllerExt<>(fragment, activityClass, intent);
    }

    /**
     * Creates the activity with {@link Bundle} and adds the fragment to the view with ID {@code contentViewId}.
     */
    private SupportFragmentControllerExt<F> create(final int contentViewId, final Bundle bundle) {
        shadowMainLooper.runPaused(() -> activityController.create(bundle).get().getSupportFragmentManager().beginTransaction().add(contentViewId, fragment).commit());
        return this;
    }

    /**
     * Creates the activity with {@link Bundle} and adds the fragment to it. Note that the fragment will be added to the view with ID 1.
     */
    public SupportFragmentControllerExt<F> create(final Bundle bundle) {
        return create(1, bundle);
    }

    @Override
    public SupportFragmentControllerExt<F> create() {
        return create(null);
    }

    @Override
    public SupportFragmentControllerExt<F> destroy() {
        shadowMainLooper.runPaused(activityController::destroy);
        return this;
    }

    public SupportFragmentControllerExt<F> start() {
        shadowMainLooper.runPaused(activityController::start);
        return this;
    }

    public SupportFragmentControllerExt<F> resume() {
        shadowMainLooper.runPaused(activityController::resume);
        return this;
    }

    public SupportFragmentControllerExt<F> pause() {
        shadowMainLooper.runPaused(activityController::pause);
        return this;
    }

    public SupportFragmentControllerExt<F> stop() {
        shadowMainLooper.runPaused(activityController::stop);
        return this;
    }

    public SupportFragmentControllerExt<F> visible() {
        shadowMainLooper.runPaused(activityController::visible);
        return this;
    }

    public SupportFragmentControllerExt<F> configurationChange(final Configuration newConfiguration) {
        shadowMainLooper.runPaused(() -> activityController.configurationChange(newConfiguration));
        return this;
    }

    private static class FragmentControllerActivity extends FragmentActivity {
        @Override
        protected void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            final LinearLayout view = new LinearLayout(this);
            view.setId(1);

            setContentView(view);
        }
    }
}
