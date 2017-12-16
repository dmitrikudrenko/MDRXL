package io.github.dmitrikudrenko.mdrxl.sample.model.data.local.repository;

import android.database.Cursor;
import rx.Observable;

public interface IRepository<C extends Cursor> {
    Observable<C> get();

    Observable<C> get(long id);
}
