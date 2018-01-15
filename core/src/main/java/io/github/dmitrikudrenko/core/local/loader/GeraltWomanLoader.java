package io.github.dmitrikudrenko.core.local.loader;

import android.content.Context;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import io.github.dmitrikudrenko.core.local.cursor.GeraltWomenCursor;
import io.github.dmitrikudrenko.core.local.repository.GeraltWomenRepository;
import io.github.dmitrikudrenko.mdrxl.loader.RxCursorLoader;
import rx.Observable;

@AutoFactory
public final class GeraltWomanLoader extends RxCursorLoader<GeraltWomenCursor> {
    private final GeraltWomenRepository repository;
    private final long id;

    GeraltWomanLoader(@Provided final Context context,
                      @Provided final GeraltWomenRepository repository,
                      final  long id) {
        super(context);
        this.repository = repository;
        this.id = id;
    }

    @Override
    protected Observable<GeraltWomenCursor> create(final String query) {
        return repository.get(id);
    }
}
