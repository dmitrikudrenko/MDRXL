package io.github.dmitrikudrenko.mdrxl.sample.model.data.local;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.UpdateModel;
import rx.Completable;
import rx.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class DataRepository {
    private final DataSqliteOpenHelper helper;

    @Inject
    DataRepository(final DataSqliteOpenHelper helper) {
        this.helper = helper;
    }

    public Observable<DataCursor> get(final long id) {
        return Observable.fromCallable(() -> {
            final SQLiteDatabase database = helper.getReadableDatabase();
            return new DataCursor(database.query(DataContract.TABLE_NAME, DataContract.PROJECTION,
                    DataContract._ID + "=?", new String[]{String.valueOf(id)},
                    null, null, null));
        });
    }

    public Completable save(final UpdateModel model) {
        return Completable.fromAction(() -> {
            final SQLiteDatabase database = helper.getWritableDatabase();
            final ContentValues cv = new ContentValues();
            model.fill(cv);
            database.update(DataContract.TABLE_NAME, cv, null, null);
        });
    }

    public Observable<DataCursor> get() {
        return Observable.fromCallable(() -> {
            final SQLiteDatabase database = helper.getReadableDatabase();
            return new DataCursor(database.query(DataContract.TABLE_NAME, DataContract.PROJECTION,
                    null, null, null, null, null));
        });
    }
}
