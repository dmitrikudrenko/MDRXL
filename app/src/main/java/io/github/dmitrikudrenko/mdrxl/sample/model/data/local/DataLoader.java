package io.github.dmitrikudrenko.mdrxl.sample.model.data.local;

import android.content.Context;
import io.github.dmitrikudrenko.mdrxl.loader.RxCursorLoader;
import rx.Observable;

import javax.inject.Inject;

public final class DataLoader extends RxCursorLoader<DataCursor> {
    private final DataRepository repository;
    private long id;

    @Inject
    DataLoader(final Context context, final DataRepository repository) {
        super(context);
        this.repository = repository;
    }

    @Override
    protected Observable<DataCursor> create(final String query) {
        return repository.get(id);
    }

    public void setId(final long id) {
        this.id = id;
    }
}
