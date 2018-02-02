package io.github.dmitrikudrenko.sample.ui.video;

import io.github.dmitrikudrenko.cast.CastManager;
import io.github.dmitrikudrenko.cast.OnSessionStartedListener;
import io.github.dmitrikudrenko.mdrxl.mvp.PresenterExtension;

public abstract class CastExtension extends PresenterExtension
        implements OnSessionStartedListener {

    private final CastManager castManager;

    public CastExtension(final CastManager castManager) {
        this.castManager = castManager;
    }

    @Override
    protected void onFirstAttach() {
        super.onFirstAttach();
        castManager.registerOnSessionStartedListener(this);
    }

    @Override
    protected void onDestroy() {
        castManager.unregisterOnSessionStartedListener(this);
        super.onDestroy();
    }

    @Override
    public void onSessionStarted() {
        sessionStarted();
    }

    protected abstract void sessionStarted();
}
