package io.github.dmitrikudrenko.cast;

import android.content.Context;
import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.gms.cast.framework.CastOptions;
import com.google.android.gms.cast.framework.OptionsProvider;
import com.google.android.gms.cast.framework.SessionProvider;
import com.google.android.gms.cast.framework.media.CastMediaOptions;
import com.google.android.gms.cast.framework.media.MediaIntentReceiver;
import com.google.android.gms.cast.framework.media.NotificationOptions;

import java.util.Arrays;
import java.util.List;

public abstract class CastOptionsProvider implements OptionsProvider {

    @Override
    public CastOptions getCastOptions(final Context context) {
        final NotificationOptions notificationOptions = createNotificationOptions();
        final CastMediaOptions mediaOptions = createMediaOptions(notificationOptions);
        final String applicationId = CastMediaControlIntent.DEFAULT_MEDIA_RECEIVER_APPLICATION_ID;
        return new CastOptions.Builder().setReceiverApplicationId(applicationId)
                .setCastMediaOptions(mediaOptions).build();
    }

    private CastMediaOptions createMediaOptions(final NotificationOptions notificationOptions) {
        return new CastMediaOptions.Builder()
                .setNotificationOptions(notificationOptions)
                .setExpandedControllerActivityClassName(expandedControllerActivityClassName())
                .build();
    }

    private NotificationOptions createNotificationOptions() {
        final List<String> actions = Arrays.asList(MediaIntentReceiver.ACTION_TOGGLE_PLAYBACK,
                MediaIntentReceiver.ACTION_STOP_CASTING,
                MediaIntentReceiver.ACTION_FORWARD);
        return new NotificationOptions.Builder()
                .setTargetActivityClassName(expandedControllerActivityClassName())
                .setActions(actions, new int[]{0, 1, 2})
                .build();
    }

    @Override
    public List<SessionProvider> getAdditionalSessionProviders(final Context context) {
        return null;
    }

    protected abstract String expandedControllerActivityClassName();
}
