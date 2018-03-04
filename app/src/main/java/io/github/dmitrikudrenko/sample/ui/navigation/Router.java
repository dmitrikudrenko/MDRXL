package io.github.dmitrikudrenko.sample.ui.navigation;

import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import io.github.dmitrikudrenko.sample.GeraltApplication;
import io.github.dmitrikudrenko.sample.R;
import io.github.dmitrikudrenko.sample.di.MultiWindow;
import io.github.dmitrikudrenko.sample.ui.settings.SettingsFragment;
import io.github.dmitrikudrenko.sample.ui.video.list.WitcherVideosFragment;
import io.github.dmitrikudrenko.sample.ui.video.player.VideoPlayerActivity;
import io.github.dmitrikudrenko.sample.ui.video.player.VideoPlayerFragment;
import io.github.dmitrikudrenko.sample.ui.video.queue.VideoQueueActivity;
import io.github.dmitrikudrenko.sample.ui.women.details.GeraltWomanActivity;
import io.github.dmitrikudrenko.sample.ui.women.details.GeraltWomanFragment;
import io.github.dmitrikudrenko.sample.ui.women.list.GeraltWomenFragment;
import io.github.dmitrikudrenko.sample.ui.women.photos.GeraltWomanPhotosActivity;
import io.github.dmitrikudrenko.sample.utils.ui.ClickInfo;

import javax.inject.Inject;
import javax.inject.Provider;

public class Router {
    private static final String TAG_CONTENT_FRAGMENT = "content_fragment";
    private static final String TAG_DETAILS_FRAGMENT = "details_fragment";

    private final Provider<AppCompatActivity> activityProvider;
    private final boolean multiWindow;

    @Inject
    Router(final Provider<AppCompatActivity> activityProvider,
           @MultiWindow final boolean multiWindow) {
        this.activityProvider = activityProvider;
        this.multiWindow = multiWindow;
    }

    @SuppressWarnings("unchecked")
    public void openGeraltWoman(final long id, final ClickInfo clickInfo) {
        GeraltApplication.createWomanComponent(id);
        final AppCompatActivity activity = activityProvider.get();
        if (multiWindow) {
            final GeraltWomanFragment fragment = GeraltWomanFragment.create();
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details, fragment, TAG_DETAILS_FRAGMENT)
                    .commitNow();
        } else {
            final View view = clickInfo.getView();
            Bundle bundle = null;
            if (view != null) {
                bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, Pair.create(view.findViewWithTag("photo"), "photo")
                ).toBundle();
            }
            activity.startActivity(GeraltWomanActivity.intent(activity), bundle);
        }
    }

    public void openGeraltWomanPhotos() {
        final AppCompatActivity activity = activityProvider.get();
        activity.startActivity(GeraltWomanPhotosActivity.intent(activity));
    }

    public void openGeraltVideo(final long itemId, final ClickInfo clickInfo) {
        GeraltApplication.createVideoComponent(itemId);
        final AppCompatActivity activity = activityProvider.get();
        if (multiWindow) {
            final VideoPlayerFragment fragment = VideoPlayerFragment.create();
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details, fragment, TAG_DETAILS_FRAGMENT)
                    .commitNow();
        } else {
            activity.startActivity(VideoPlayerActivity.intent(activity));
        }
    }

    public void openVideoQueue() {
        final AppCompatActivity activity = activityProvider.get();
        activity.startActivity(VideoQueueActivity.intent(activity));
    }

    public void openGeraltWomen() {
        final AppCompatActivity activity = activityProvider.get();
        final GeraltWomenFragment fragment = new GeraltWomenFragment();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment, TAG_CONTENT_FRAGMENT)
                .commitNow();
        if (multiWindow) {
            removeDetailsFragment(activity);
        }
    }

    public void openSettings() {
        final AppCompatActivity activity = activityProvider.get();
        final SettingsFragment fragment = new SettingsFragment();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment, TAG_CONTENT_FRAGMENT)
                .commitNow();
        if (multiWindow) {
            removeDetailsFragment(activity);
        }
    }

    public void openWitcherVideos() {
        final AppCompatActivity activity = activityProvider.get();
        final WitcherVideosFragment fragment = new WitcherVideosFragment();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment, TAG_CONTENT_FRAGMENT)
                .commitNow();
        if (multiWindow) {
            removeDetailsFragment(activity);
        }
    }

    private void removeDetailsFragment(final AppCompatActivity activity) {
        final FragmentManager fm = activity.getSupportFragmentManager();
        final Fragment detailsFragment = fm.findFragmentByTag(TAG_DETAILS_FRAGMENT);
        if (detailsFragment != null) {
            fm.beginTransaction().remove(detailsFragment).commitNow();
        }
    }
}
