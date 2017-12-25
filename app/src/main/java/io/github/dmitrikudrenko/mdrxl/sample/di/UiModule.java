package io.github.dmitrikudrenko.mdrxl.sample.di;

import android.content.Context;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import dagger.Module;
import dagger.Provides;
import io.github.dmitrikudrenko.mdrxl.sample.utils.CircleTransform;

@Module
class UiModule {
    @Provides
    Picasso providePicasso(final Context context) {
        return Picasso.with(context);
    }

    @PhotoTransformation
    @Provides
    Transformation providePhotoTransformation() {
        return new CircleTransform();
    }
}
