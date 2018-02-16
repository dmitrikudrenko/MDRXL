package io.github.dmitrikudrenko.core.local.loader.video;

import android.content.Context;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import io.github.dmitrikudrenko.core.local.cursor.WitcherVideoCursor;
import io.github.dmitrikudrenko.core.local.repository.WitcherVideoRepository;
import io.github.dmitrikudrenko.mdrxl.loader.RxCursorLoader;
import rx.Observable;

@AutoFactory
public class WitcherVideoLoader extends RxCursorLoader<WitcherVideoCursor> {
    private final WitcherVideoRepository repository;
    private final long id;

    public WitcherVideoLoader(@Provided final Context context,
                              @Provided final WitcherVideoRepository repository,
                              final long id) {
        super(context);
        this.repository = repository;
        this.id = id;
    }

    @Override
    protected Observable<WitcherVideoCursor> create(final String query) {
        return repository.getVideo(id);
    }
}
