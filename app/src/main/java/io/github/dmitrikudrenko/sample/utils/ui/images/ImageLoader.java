package io.github.dmitrikudrenko.sample.utils.ui.images;

import android.widget.ImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;
import dagger.Lazy;
import io.github.dmitrikudrenko.sample.R;
import io.github.dmitrikudrenko.sample.di.DimThumbnailTransformation;
import io.github.dmitrikudrenko.sample.di.PhotoTransformation;
import io.github.dmitrikudrenko.sample.di.ThumbnailTransformation;
import io.github.dmitrikudrenko.utils.common.Strings;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

import static io.github.dmitrikudrenko.utils.common.Strings.nullIfBlank;

@Singleton
public class ImageLoader {
    private final Picasso picasso;
    private final Lazy<Transformation> photoTransformation;
    private final Lazy<Transformation> thumbnailTransformation;
    private final Lazy<Transformation> dimThumbnailTransformation;

    @Inject
    ImageLoader(final Picasso picasso,
                @PhotoTransformation final Lazy<Transformation> photoTransformation,
                @ThumbnailTransformation final Lazy<Transformation> thumbnailTransformation,
                @DimThumbnailTransformation final Lazy<Transformation> dimThumbnailTransformation) {
        this.picasso = picasso;
        this.photoTransformation = photoTransformation;
        this.thumbnailTransformation = thumbnailTransformation;
        this.dimThumbnailTransformation = dimThumbnailTransformation;
    }

    private RequestCreator load(final String url) {
        return picasso.load(nullIfBlank(url));
    }

    public void loadPhotoInto(final String url, @Nullable final String placeholder,
                              final ImageView imageView) {
        final RequestCreator originalLoading = loadPhotoInto(load(url));
        if (Strings.isNotBlank(placeholder)) {
            final RequestCreator placeholderLoading = loadPhotoInto(load(placeholder));
            placeholderLoading.into(imageView, new Callback.EmptyCallback() {
                @Override
                public void onSuccess() {
                    originalLoading.into(imageView);
                }
            });
        } else {
            originalLoading.into(imageView);
        }
    }

    private RequestCreator loadPhotoInto(final RequestCreator requestCreator) {
        return requestCreator.fit().centerCrop()
                .transform(photoTransformation.get());
    }

    public void loadFullSizeImageInto(final String url, final ImageView imageView) {
        load(url).into(imageView);
    }

    public void loadFitCroppedImageInto(final String url, @Nullable final String placeholder,
                                        final ImageView imageView) {
        final RequestCreator originalLoading = loadFitCroppedImageInto(load(url));
        if (Strings.isNotBlank(placeholder)) {
            final RequestCreator placeholderLoading = loadFitCroppedImageInto(load(placeholder));
            placeholderLoading.into(imageView, new Callback.EmptyCallback() {
                @Override
                public void onSuccess() {
                    originalLoading.into(imageView);
                }
            });
        } else {
            originalLoading.into(imageView);
        }
    }

    private RequestCreator loadFitCroppedImageInto(final RequestCreator requestCreator) {
        return requestCreator.fit().centerCrop();
    }

    public void loadThumbnailInto(final String url, final ImageView imageView) {
        load(url)
                .fit()
                .centerCrop()
                .transform(thumbnailTransformation.get())
                .into(imageView);
    }

    public void loadDimThumbnailInto(final String url, final ImageView imageView) {
        load(url)
                .fit()
                .centerCrop()
                .transform(dimThumbnailTransformation.get())
                .placeholder(R.color.videoActionsForegroundDefault)
                .into(imageView);
    }
}
