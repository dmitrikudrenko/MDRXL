package io.github.dmitrikudrenko.mdrxl.sample.model;

import android.content.Context;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import rx.Observable;

import javax.inject.Inject;

public final class DataLoader extends RxLoader<Data> {
    private final DataRepository repository;

    @Inject
    DataLoader(final Context context, final DataRepository repository) {
        super(context);
        this.repository = repository;
    }

    @Override
    protected Observable<Data> create() {
        return repository.get();
    }
}
