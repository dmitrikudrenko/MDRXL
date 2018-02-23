package io.github.dmitrikudrenko.sample.utils.ui.images;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.request.RequestOptions;
import dagger.Lazy;
import io.github.dmitrikudrenko.sample.di.DimThumbnailTransformation;
import io.github.dmitrikudrenko.sample.di.PhotoTransformation;
import io.github.dmitrikudrenko.sample.di.ThumbnailTransformation;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ImageLoader {
    private final Context context;
    private final Lazy<Transformation> photoTransformation;
    private final Lazy<Transformation> thumbnailTransformation;
    private final Lazy<Transformation> dimThumbnailTransformation;

    @Inject
    ImageLoader(final Context context,
                @PhotoTransformation final Lazy<Transformation> photoTransformation,
                @ThumbnailTransformation final Lazy<Transformation> thumbnailTransformation,
                @DimThumbnailTransformation final Lazy<Transformation> dimThumbnailTransformation) {
        this.context = context;
        this.photoTransformation = photoTransformation;
        this.thumbnailTransformation = thumbnailTransformation;
        this.dimThumbnailTransformation = dimThumbnailTransformation;
    }

    public void loadPhotoInto(final String url, final ImageView imageView) {
        Glide.with(context).load(url)
                .apply(RequestOptions.fitCenterTransform())
                .apply(RequestOptions.centerCropTransform())
                .apply(RequestOptions.bitmapTransform(photoTransformation.get()))
                .into(imageView);
    }

    public void loadFullSizeImageInto(final String url, final ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

    public void loadFitCroppedImageInto(final String url, final ImageView imageView) {
        Glide.with(context).load(url)
                .apply(RequestOptions.fitCenterTransform())
                .apply(RequestOptions.centerCropTransform())
                .into(imageView);
    }

    public void loadThumbnailInto(final String url, final ImageView imageView) {
        Glide.with(context).load(url)
                .apply(RequestOptions.fitCenterTransform())
                .apply(RequestOptions.centerCropTransform())
                .apply(RequestOptions.bitmapTransform(thumbnailTransformation.get()))
                .into(imageView);
    }

    public void loadDimThumbnailInto(final String url, final ImageView imageView) {
        Glide.with(context).load(url)
                .apply(RequestOptions.fitCenterTransform())
                .apply(RequestOptions.centerCropTransform())
                .apply(RequestOptions.bitmapTransform(dimThumbnailTransformation.get()))
//                .placeholder(R.color.videoActionsForegroundDefault)
                .into(imageView);
    }
}
