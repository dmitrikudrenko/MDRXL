package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local;

import android.content.Context;
import io.github.dmitrikudrenko.mdrxl.loader.RxCursorLoader;
import rx.Observable;

import javax.inject.Inject;

public final class GeraltWomenLoader extends RxCursorLoader<GeraltWomenCursor> {
    private final GeraltWomenRepository repository;

    @Inject
    GeraltWomenLoader(final Context context, final GeraltWomenRepository repository) {
        super(context);
        this.repository = repository;
    }

    @Override
    protected Observable<GeraltWomenCursor> create(final String query) {
        return repository.get(query);
    }
}
