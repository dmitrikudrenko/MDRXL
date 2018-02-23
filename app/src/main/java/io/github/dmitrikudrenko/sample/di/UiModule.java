package io.github.dmitrikudrenko.sample.di;

import android.content.Context;
import android.content.res.Resources;
import com.bumptech.glide.load.Transformation;
import dagger.Module;
import dagger.Provides;
import io.github.dmitrikudrenko.core.events.EventSender;
import io.github.dmitrikudrenko.core.events.EventSource;
import io.github.dmitrikudrenko.sample.R;
import io.github.dmitrikudrenko.sample.utils.EventBus;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

@Module
class UiModule {

//    @Provides
//    RequestManager provideGlide(final Context context) {
//        return Glide.with(context);
//    }

    @PhotoTransformation
    @Provides
    Transformation providePhotoTransformation() {
        return new CropCircleTransformation();
    }

    @ThumbnailTransformation
    @Provides
    Transformation provideThumbnailTransformation(final Resources resources) {
        return new RoundedCornersTransformation((int) resources.getDimension(R.dimen.thumbnail_corner), 0);
    }

    @DimThumbnailTransformation
    @Provides
    Transformation provideDimThumbnailTransformation(final Resources resources) {
        return new ColorFilterTransformation(resources.getColor(R.color.videoActionsForeground));
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
