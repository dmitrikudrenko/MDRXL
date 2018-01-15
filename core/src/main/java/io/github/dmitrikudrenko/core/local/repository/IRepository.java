package io.github.dmitrikudrenko.core.local.repository;

import android.database.Cursor;
import rx.Observable;

public interface IRepository<C extends Cursor> {
    Observable<C> get();

    Observable<C> get(String query);

    Observable<C> get(long id);
}