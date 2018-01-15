package io.github.dmitrikudrenko.mdrxl.sample.di;

import android.content.Context;
import android.content.res.Resources;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import dagger.Module;
import dagger.Provides;
import io.github.dmitrikudrenko.core.events.EventSender;
import io.github.dmitrikudrenko.core.events.EventSource;
import io.github.dmitrikudrenko.mdrxl.sample.utils.EventBus;
import io.github.dmitrikudrenko.mdrxl.sample.utils.ui.images.CircleTransform;
import okhttp3.OkHttpClient;

@Module
class UiModule {

    @Provides
    Picasso providePicasso(final Context context, final OkHttpClient httpClient) {
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(httpClient))
                .build();
    }

    @PhotoTransformation
    @Provides
    Transformation providePhotoTransformation() {
        return new CircleTransform();
    }

    @Provides
    Resources provideResources(final Context context) {
        return context.getResources();
    }

    @Provides
    EventSender provideEventSender() {
        return new EventBus(org.greenrobot.eventbus.EventBus.getDefault());
    }

    @Provides
    EventSource provideEventSource(final EventSender eventSender) {
        return (EventSource) eventSender;
    }
}
