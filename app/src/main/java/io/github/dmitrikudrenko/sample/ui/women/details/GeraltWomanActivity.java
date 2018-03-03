package io.github.dmitrikudrenko.sample.ui.women.details;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.Pair;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.dmitrikudrenko.sample.GeraltApplication;
import io.github.dmitrikudrenko.sample.R;
import io.github.dmitrikudrenko.sample.ui.base.BaseFragmentHolderRxActivity;
import io.github.dmitrikudrenko.sample.utils.ui.TransitionListenerAdapter;
import io.github.dmitrikudrenko.sample.utils.ui.ViewUtils;
import io.github.dmitrikudrenko.sample.utils.ui.images.ImageLoader;

import javax.inject.Inject;

public class GeraltWomanActivity extends BaseFragmentHolderRxActivity<GeraltWomanFragment> {
    private static final int TRANSITION_DURATION = 330;

    public static Intent intent(final Context context) {
        return new Intent(context, GeraltWomanActivity.class);
    }

    @Inject
    ImageLoader imageLoader;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.toolbar_image)
    ImageView toolbarImageView;

    @BindView(R.id.reveal_image)
    View revealImage;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState != null) {
            revealImage.setVisibility(View.INVISIBLE);
            toolbarImageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void beforeContentViewSet() {
        setTransitions();
    }

    @Override
    protected GeraltWomanFragment createFragment() {
        return GeraltWomanFragment.create();
    }

    @Override
    protected void beforeOnCreate(final Bundle savedInstanceState) {
        GeraltApplication.get().inject(this);
    }

    @Override
    protected int contentView() {
        return R.layout.a_fragment_holder_collapsing;
    }

    void loadPhotoIntoToolbar(final String url) {
        imageLoader.loadFitCroppedImageInto(url, toolbarImageView);
    }

    public void showTitle(final String value) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(value);
        }
        collapsingToolbarLayout.setTitle(value);
    }

    @OnClick(R.id.toolbar_image)
    void onPhotoClicked() {
        getFragment().onPhotoClicked();
    }

    private void setTransitions() {
        final TransitionInflater inflater = TransitionInflater.from(this);
        final Transition transition = inflater.inflateTransition(R.transition.arc);

        final TransitionSet enterSet = new TransitionSet();
        enterSet.addTransition(transition);
        enterSet.setInterpolator(new AccelerateDecelerateInterpolator());
        enterSet.setDuration(TRANSITION_DURATION);
        enterSet.addListener(new TransitionListenerAdapter() {
            @Override
            public void onTransitionEnd(final Transition transition) {
                reveal();
            }
        });

        final TransitionSet exitSet = new TransitionSet();
        exitSet.addTransition(transition);
        exitSet.setDuration(TRANSITION_DURATION);
        exitSet.setInterpolator(new AccelerateDecelerateInterpolator());
        exitSet.setStartDelay(200);

        getWindow().setSharedElementEnterTransition(enterSet);
        getWindow().setSharedElementExitTransition(exitSet);
        getWindow().setSharedElementReturnTransition(exitSet);
    }

    private void reveal() {
        appBarLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        final Pair<Float, Float> center = ViewUtils.getCenter(revealImage);
        final Animator animator = ViewAnimationUtils.createCircularReveal(appBarLayout,
                center.first.intValue(), center.second.intValue(), 0, appBarLayout.getWidth());
        animator.setDuration(TRANSITION_DURATION);
        toolbarImageView.setVisibility(View.VISIBLE);
        animator.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideView();
    }

    private void hideView() {
        final Pair<Float, Float> center = ViewUtils.getCenter(revealImage);
        final Animator animator = ViewAnimationUtils.createCircularReveal(toolbarImageView,
                center.first.intValue(), center.second.intValue(), toolbarImageView.getWidth(), 0);
        animator.setDuration(TRANSITION_DURATION);
        animator.start();
    }
}
