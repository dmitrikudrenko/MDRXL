package io.github.dmitrikudrenko.sample.ui.video.player;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import io.github.dmitrikudrenko.sample.ui.base.BaseFragmentHolderRxActivity;

public class VideoPlayerActivity extends BaseFragmentHolderRxActivity<VideoPlayerFragment> {
    public static Intent intent(final Context context) {
        return new Intent(context, VideoPlayerActivity.class);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected VideoPlayerFragment createFragment() {
        return VideoPlayerFragment.create();
    }

    public void showActionBar() {
        getToolbar().animate().translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(final Animator animation) {
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().show();
                        }
                    }
                })
                .start();
    }

    public void hideActionBar() {
        final Toolbar toolbar = getToolbar();
        toolbar.animate().translationY(-toolbar.getBottom())
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(final Animator animation) {
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().hide();
                        }
                    }
                })
                .start();
    }
}
