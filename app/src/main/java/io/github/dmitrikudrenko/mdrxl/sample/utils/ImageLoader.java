package io.github.dmitrikudrenko.mdrxl.sample.utils;

import android.widget.ImageView;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ImageLoader {
    private final Picasso picasso;
    private final ImageTransformer imageTransformer;

    @Inject
    public ImageLoader(final Picasso picasso, final ImageTransformer imageTransformer) {
        this.picasso = picasso;
        this.imageTransformer = imageTransformer;
    }

    public void loadPhotoInto(final String url, final ImageView imageView) {
        imageTransformer.photoTransform(picasso.load(url)).into(imageView);
    }
}
