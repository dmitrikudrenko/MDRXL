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

public class Router {
    private static final String TAG_CONTENT_FRAGMENT = "content_fragment";
    private static final String TAG_DETAILS_FRAGMENT = "details_fragment";

    private final AppCompatActivity activity;
    private final boolean multiWindow;

    @Inject
    Router(final AppCompatActivity activity,
           @MultiWindow final boolean multiWindow) {
        this.activity = activity;
        this.multiWindow = multiWindow;
    }

    @SuppressWarnings("unchecked")
    public void openGeraltWoman(final long id, final ClickInfo clickInfo) {
        GeraltApplication.createWomanComponent(id);
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
                        activity, Pair.create(view.findViewById(R.id.photo), "photo")
                ).toBundle();
            }
            activity.startActivity(GeraltWomanActivity.intent(activity), bundle);
        }
    }

    public void openGeraltWomanPhotos() {
        activity.startActivity(GeraltWomanPhotosActivity.intent(activity));
    }

    public void openGeraltVideo(final long itemId, final ClickInfo clickInfo) {
        GeraltApplication.createVideoComponent(itemId);
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
        activity.startActivity(VideoQueueActivity.intent(activity));
    }

    public void openGeraltWomen() {
        final GeraltWomenFragment fragment = new GeraltWomenFragment();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment, TAG_CONTENT_FRAGMENT)
                .commitNow();
        if (multiWindow) {
            removeDetailsFragment();
        }
    }

    public void openSettings() {
        final SettingsFragment fragment = new SettingsFragment();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment, TAG_CONTENT_FRAGMENT)
                .commitNow();
        if (multiWindow) {
            removeDetailsFragment();
        }
    }

    public void openWitcherVideos() {
        final WitcherVideosFragment fragment = new WitcherVideosFragment();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment, TAG_CONTENT_FRAGMENT)
                .commitNow();
        if (multiWindow) {
            removeDetailsFragment();
        }
    }

    private void removeDetailsFragment() {
        final FragmentManager fm = activity.getSupportFragmentManager();
        final Fragment detailsFragment = fm.findFragmentByTag(TAG_DETAILS_FRAGMENT);
        if (detailsFragment != null) {
            fm.beginTransaction().remove(detailsFragment).commitNow();
        }
    }
}
