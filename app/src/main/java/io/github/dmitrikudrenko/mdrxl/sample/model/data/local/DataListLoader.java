package io.github.dmitrikudrenko.mdrxl.sample.model.data.local;

import android.content.Context;
import io.github.dmitrikudrenko.mdrxl.loader.RxCursorLoader;
import rx.Observable;

import javax.inject.Inject;

public final class DataListLoader extends RxCursorLoader<DataCursor> {
    private final DataRepository repository;

    @Inject
    DataListLoader(final Context context, final DataRepository repository) {
        super(context);
        this.repository = repository;
    }

    @Override
    protected Observable<DataCursor> create() {
        return repository.get();
    }
}
