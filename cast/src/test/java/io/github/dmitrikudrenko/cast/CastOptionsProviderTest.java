package io.github.dmitrikudrenko.cast;

import com.google.android.gms.cast.framework.CastOptions;
import com.google.android.gms.cast.framework.media.CastMediaOptions;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.List;

import static junit.framework.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class CastOptionsProviderTest {
    String expandedControllerActivityClassName = "expanded_controller_activity_class_name";
    CastOptionsProvider provider;

    @Before
    public void setUp() {
        provider = new CastOptionsProvider() {
            @Override
            protected String expandedControllerActivityClassName() {
                return expandedControllerActivityClassName;
            }
        };
    }

    @Test
    public void shouldNotBeAdditionalSessionProviders() {
        assertNull(provider.getAdditionalSessionProviders(RuntimeEnvironment.systemContext));
    }

    @Test
    public void shouldBeDefaultReceiverApplicationId() {
        final CastOptions castOptions = provider.getCastOptions(RuntimeEnvironment.systemContext);
        assertEquals("CC1AD845", castOptions.getReceiverApplicationId());
    }

    @Test
    public void checkCastOptions() {
        final CastOptions castOptions = provider.getCastOptions(RuntimeEnvironment.systemContext);
        final CastMediaOptions castMediaOptions = castOptions.getCastMediaOptions();
        final NotificationOptions notificationOptions = castMediaOptions.getNotificationOptions();

        assertEquals(expandedControllerActivityClassName, castMediaOptions.getExpandedControllerActivityClassName());
        assertEquals(expandedControllerActivityClassName, notificationOptions.getTargetActivityClassName());

        final List<String> notificationActions = notificationOptions.getActions();
        assertTrue(notificationActions.contains("com.google.android.gms.cast.framework.action.TOGGLE_PLAYBACK"));
        assertTrue(notificationActions.contains("com.google.android.gms.cast.framework.action.STOP_CASTING"));
        assertTrue(notificationActions.contains("com.google.android.gms.cast.framework.action.FORWARD"));
    }
}
