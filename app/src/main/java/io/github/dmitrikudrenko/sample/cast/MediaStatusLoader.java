package io.github.dmitrikudrenko.sample.cast;

import android.content.Context;
import io.github.dmitrikudrenko.cast.CastManager;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

import javax.inject.Inject;

public class MediaStatusLoader extends RxLoader<MediaQueue> {
    private final CastManager castManager;

    @Inject
    public MediaStatusLoader(final Context context, final CastManager castManager) {
        super(context);
        this.castManager = castManager;
    }

    @Override
    protected Observable<MediaQueue> create(final String query) {
        return castManager.getQueue().map(MediaQueue::new);
    }

    @Override
    protected Scheduler subscribeOn() {
        return AndroidSchedulers.mainThread();
    }
}
