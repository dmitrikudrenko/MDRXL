package io.github.dmitrikudrenko.core.local.loader;

import android.content.Context;
import io.github.dmitrikudrenko.core.local.cursor.GeraltWomenCursor;
import io.github.dmitrikudrenko.core.local.repository.GeraltWomenRepository;
import io.github.dmitrikudrenko.mdrxl.loader.RxCursorLoader;
import rx.Observable;

import javax.inject.Inject;

public class GeraltWomenLoader extends RxCursorLoader<GeraltWomenCursor> {
    private final GeraltWomenRepository repository;

    @Inject
    public GeraltWomenLoader(final Context context, final GeraltWomenRepository repository) {
        super(context);
        this.repository = repository;
    }

    @Override
    protected Observable<GeraltWomenCursor> create(final String query) {
        return repository.getWomen(query);
    }
}
