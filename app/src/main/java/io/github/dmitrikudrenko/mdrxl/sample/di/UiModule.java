package io.github.dmitrikudrenko.mdrxl.sample.di;

import android.content.Context;
import android.content.res.Resources;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import dagger.Module;
import dagger.Provides;
import io.github.dmitrikudrenko.mdrxl.sample.utils.ui.images.CircleTransform;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

@Module
class UiModule {
    private static final long CACHE_SIZE = 100 * 1024 * 1024;

    @Provides
    Picasso providePicasso(final Context context, final OkHttpClient httpClient) {
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(httpClient))
                .build();
    }

    @Provides
    OkHttpClient provideHttpClient(final Context context) {
        return new OkHttpClient.Builder()
                .cache(new Cache(context.getCacheDir(), CACHE_SIZE))
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
}
