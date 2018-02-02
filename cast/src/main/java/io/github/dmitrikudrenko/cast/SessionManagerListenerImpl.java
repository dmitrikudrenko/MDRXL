package io.github.dmitrikudrenko.cast;

import android.support.annotation.CallSuper;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.Session;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;

import javax.annotation.Nullable;

import static android.util.Log.d;
import static io.github.dmitrikudrenko.utils.common.Preconditions.checkNotNull;

public class SessionManagerListenerImpl implements SessionManagerListener<Session> {
    private static final String TAG = "SessionManagerListener";

    @Nullable
    private CastSession castSession;

    @Nullable
    private OnSessionStartedListener onSessionStartedListener;

    @Override
    public void onSessionStarting(final Session session) {
        d(TAG, "onSessionStarting");
    }

    @CallSuper
    @Override
    public void onSessionStarted(final Session session, final String s) {
        d(TAG, "onSessionStarted");
        castSession = (CastSession) session;
        if (onSessionStartedListener != null) {
            onSessionStartedListener.onSessionStarted();
        }
    }

    @Override
    public void onSessionStartFailed(final Session session, final int i) {
        d(TAG, "onSessionStartFailed");
    }

    @Override
    public void onSessionEnding(final Session session) {
        d(TAG, "onSessionEnding");
    }

    @Override
    public void onSessionEnded(final Session session, final int i) {
        d(TAG, "onSessionEnded");
        if (castSession == session) {
            castSession = null;
        }
    }

    @Override
    public void onSessionResuming(final Session session, final String s) {
        d(TAG, "onSessionResuming");
    }

    @CallSuper
    @Override
    public void onSessionResumed(final Session session, final boolean b) {
        d(TAG, "onSessionResumed");
        castSession = (CastSession) session;
    }

    @Override
    public void onSessionResumeFailed(final Session session, final int i) {
        d(TAG, "onSessionResumeFailed");
    }

    @Override
    public void onSessionSuspended(final Session session, final int i) {
        d(TAG, "onSessionSuspended");
    }

    RemoteMediaClient getRemoteMediaClient() {
        return checkNotNull(castSession).getRemoteMediaClient();
    }

    boolean isConnected() {
        return castSession != null && castSession.isConnected();
    }

    public void setOnSessionStartedListener(final OnSessionStartedListener l) {
        this.onSessionStartedListener = l;
    }
}
