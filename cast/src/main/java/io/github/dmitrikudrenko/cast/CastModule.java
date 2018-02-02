package io.github.dmitrikudrenko.cast;

import android.content.Context;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.SessionManager;
import dagger.Module;
import dagger.Provides;

@Module
public class CastModule {
    @Provides
    SessionManager provideSessionManager(final Context context) {
        return CastContext.getSharedInstance(context).getSessionManager();
    }

    @Provides
    SessionManagerListenerImpl provideSessionManagerListener(final SessionManager sessionManager) {
        final SessionManagerListenerImpl l = new SessionManagerListenerImpl();
        sessionManager.addSessionManagerListener(l);
        return l;
    }
}
