package io.github.dmitrikudrenko.sample.utils.ui.images;

import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import dagger.Lazy;
import io.github.dmitrikudrenko.sample.R;
import io.github.dmitrikudrenko.sample.di.DimThumbnailTransformation;
import io.github.dmitrikudrenko.sample.di.PhotoTransformation;
import io.github.dmitrikudrenko.sample.di.ThumbnailTransformation;

import javax.inject.Inject;
import javax.inject.Singleton;

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

    public void loadPhotoInto(final String url, final ImageView imageView) {
        picasso.load(url)
                .fit()
                .centerCrop()
                .transform(photoTransformation.get())
                .into(imageView);
    }

    public void loadFullSizeImageInto(final String url, final ImageView imageView) {
        picasso.load(url).into(imageView);
    }

    public void loadFitCroppedImageInto(final String url, final ImageView imageView) {
        picasso.load(url).fit().centerCrop().into(imageView);
    }

    public void loadThumbnailInto(final String url, final ImageView imageView) {
        picasso.load(url)
                .fit()
                .centerCrop()
                .transform(thumbnailTransformation.get())
                .into(imageView);
    }

    public void loadDimThumbnailInto(final String url, final ImageView imageView) {
        picasso.load(url)
                .fit()
                .centerCrop()
                .transform(dimThumbnailTransformation.get())
                .placeholder(R.color.videoActionsForegroundDefault)
                .into(imageView);
    }
}
