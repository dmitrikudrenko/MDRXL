package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local;

import android.content.Context;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import io.github.dmitrikudrenko.mdrxl.loader.RxCursorLoader;
import rx.Observable;

@AutoFactory
public class GeraltWomanPhotoLoader extends RxCursorLoader<GeraltWomanPhotoCursor> {
    private final GeraltWomanPhotoRepository repository;
    private final long id;

    GeraltWomanPhotoLoader(@Provided final Context context,
                           @Provided final GeraltWomanPhotoRepository repository,
                           final long id) {
        super(context);
        this.repository = repository;
        this.id = id;
    }

    @Override
    protected Observable<GeraltWomanPhotoCursor> create(final String query) {
        return repository.get(id);
    }
}
