package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local;

import android.content.Context;
import io.github.dmitrikudrenko.mdrxl.loader.RxCursorLoader;
import rx.Observable;

import javax.inject.Inject;

public final class GeraltWomanLoader extends RxCursorLoader<GeraltWomenCursor> {
    private final GeraltWomenRepository repository;
    private long id;

    @Inject
    GeraltWomanLoader(final Context context, final GeraltWomenRepository repository) {
        super(context);
        this.repository = repository;
    }

    @Override
    protected Observable<GeraltWomenCursor> create(final String query) {
        return repository.get(id);
    }

    public void setId(final long id) {
        this.id = id;
    }
}
