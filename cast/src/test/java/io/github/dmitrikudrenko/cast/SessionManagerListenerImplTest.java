package io.github.dmitrikudrenko.cast;

import com.google.android.gms.cast.framework.CastSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class SessionManagerListenerImplTest {
    SessionManagerListenerImpl listener;
    CastSession session;

    @Before
    public void setUp() {
        listener = new SessionManagerListenerImpl();
        session = mock(CastSession.class);
        when(session.isConnected()).thenReturn(true);
    }

    @Test
    public void onSessionLifecycle() {
        listener.onSessionStarting(session);
        assertFalse(listener.isConnected());

        listener.onSessionResumed(session, true);
        assertTrue(listener.isConnected());

        listener.onSessionEnded(session, 0);
        assertFalse(listener.isConnected());
    }
}
