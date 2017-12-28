package io.github.dmitrikudrenko.mdrxl.sample.utils.ui.images;

import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;
import dagger.Lazy;
import io.github.dmitrikudrenko.mdrxl.sample.di.PhotoTransformation;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ImageTransformer {

    private final Lazy<Transformation> photoTransformation;

    @Inject
    ImageTransformer(@PhotoTransformation final Lazy<Transformation> photoTransformation) {
        this.photoTransformation = photoTransformation;
    }

    public RequestCreator photoTransform(final RequestCreator requestCreator) {
        return requestCreator.fit().centerCrop().transform(photoTransformation.get());
    }
}
