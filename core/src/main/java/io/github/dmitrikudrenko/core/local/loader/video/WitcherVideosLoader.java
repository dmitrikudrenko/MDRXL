package io.github.dmitrikudrenko.core.local.loader.video;

import android.content.Context;
import io.github.dmitrikudrenko.core.local.cursor.WitcherVideoCursor;
import io.github.dmitrikudrenko.core.local.repository.WitcherVideoRepository;
import io.github.dmitrikudrenko.mdrxl.loader.RxCursorLoader;
import rx.Observable;

import javax.inject.Inject;

public class WitcherVideosLoader extends RxCursorLoader<WitcherVideoCursor> {
    private final WitcherVideoRepository repository;

    @Inject
    public WitcherVideosLoader(final Context context, final WitcherVideoRepository repository) {
        super(context);
        this.repository = repository;
    }

    @Override
    protected Observable<WitcherVideoCursor> create(final String query) {
        return repository.getVideos();
    }
}
