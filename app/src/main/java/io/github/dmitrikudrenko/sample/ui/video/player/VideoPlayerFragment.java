package io.github.dmitrikudrenko.sample.ui.video.player;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.devbrackets.android.exomedia.listener.VideoControlsVisibilityListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.google.android.gms.cast.framework.CastButtonFactory;
import dagger.Provides;
import dagger.Subcomponent;
import io.github.dmitrikudrenko.cast.CastButtonFactoryExt;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.sample.GeraltApplication;
import io.github.dmitrikudrenko.sample.R;
import io.github.dmitrikudrenko.sample.di.FragmentScope;
import io.github.dmitrikudrenko.sample.di.MultiWindow;
import io.github.dmitrikudrenko.sample.di.OptionsMenu;
import io.github.dmitrikudrenko.sample.ui.base.BaseRxFragment;
import io.github.dmitrikudrenko.sample.ui.video.queue.VideoQueueActivity;
import io.github.dmitrikudrenko.sample.utils.ui.messages.MessageFactory;
import io.github.dmitrikudrenko.sample.utils.ui.messages.ToastFactory;

import javax.inject.Inject;

public class VideoPlayerFragment extends BaseRxFragment implements VideoPlayerView,
        VideoControlsVisibilityListener, PlayerObserver {

    @Inject
    @InjectPresenter
    VideoPlayerPresenter presenter;

    @Inject
    @OptionsMenu
    int optionsMenu;

    @BindView(R.id.player)
    VideoView playerView;

    @ProvidePresenter
    public VideoPlayerPresenter providePresenter() {
        return presenter;
    }

    public static VideoPlayerFragment create() {
        return new VideoPlayerFragment();
    }

    @Override
    protected void beforeOnCreate(final Bundle savedInstanceState) {
        GeraltApplication.getVideoComponent().plus(new Module()).inject(this);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(optionsMenu, menu);
        CastButtonFactory.setUpMediaRouteButton(getContext(), menu, R.id.media_route_menu_item);
        CastButtonFactoryExt.setUpCastDependentButton(getContext(), menu, R.id.queue);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.queue) {
            startActivity(VideoQueueActivity.intent(getActivity()));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void stopPlayer() {
        playerView.stopPlayback();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_video_player, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupPlayerView();
    }

    private void setupPlayerView() {
        playerView.setOnPreparedListener(() -> playerView.start());
        if (playerView.getVideoControls() != null) {
            playerView.getVideoControls().setVisibilityListener(this);
            playerView.getVideoControls().setHideDelay(4000);
        }
    }

    @Override
    public long getCurrentPosition() {
        return playerView.getCurrentPosition();
    }

    @Override
    public void showVideo(final String title, final String url, final long startPosition) {
        playerView.setVideoURI(Uri.parse(url));
        playerView.seekTo(startPosition);
        if (playerView.getVideoControls() != null) {
            playerView.getVideoControls().setTitle(title);
        }
    }

    @Override
    public void hideActionBar() {
        ((VideoPlayerActivity) getActivity()).hideActionBar();
    }

    @Override
    public void showActionBar() {
        ((VideoPlayerActivity) getActivity()).showActionBar();
    }

    @Override
    public void onDestroyView() {
        playerView.release();
        super.onDestroyView();
    }

    @Override
    public void onControlsShown() {
        presenter.onControlsShown();
    }

    @Override
    public void onControlsHidden() {
        presenter.onControlsHidden();
    }

    @dagger.Module
    public class Module {
        @Provides
        RxLoaderManager provideLoaderManager() {
            return new RxLoaderManager(getLoaderManager());
        }

        @Provides
        MessageFactory provideMessageFactory() {
            return new ToastFactory(getContext());
        }

        @OptionsMenu
        @Provides
        int getOptionsMenu(@MultiWindow final boolean multiwindow) {
            return multiwindow ? R.menu.m_cast : R.menu.m_expanded_controllers;
        }
    }

    @FragmentScope
    @Subcomponent(modules = Module.class)
    public interface Component {
        void inject(VideoPlayerFragment fragment);
    }
}
