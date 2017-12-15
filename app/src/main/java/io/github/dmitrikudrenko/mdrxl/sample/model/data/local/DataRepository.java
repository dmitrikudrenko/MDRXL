package io.github.dmitrikudrenko.mdrxl.sample.model.data.local;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.Data;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.UpdateModel;
import rx.Completable;
import rx.Observable;
import rx.subjects.BehaviorSubject;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class DataRepository {
    private final DataSqliteOpenHelper helper;
    private final BehaviorSubject<Data> subject = BehaviorSubject.create();

    @Inject
    DataRepository(final DataSqliteOpenHelper helper) {
        this.helper = helper;
        subject.onNext(getSync());
    }

    public Observable<Data> get() {
        return subject;
    }

    private Data getSync() {
        final SQLiteDatabase database = helper.getReadableDatabase();
        DataCursor cursor = null;
        try {
            cursor = new DataCursor(database.query(DataContract.TABLE_NAME, DataContract.PROJECTION,
                    null, null, null, null, null));
            if (cursor.moveToNext()) {
                return new Data(
                        cursor.getId(),
                        cursor.getName(),
                        cursor.getFirstAttribute(),
                        cursor.getSecondAttribute(),
                        cursor.getThirdAttribute()
                );
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public Completable save(final UpdateModel model) {
        return Completable.fromAction(() -> {
            final SQLiteDatabase database = helper.getWritableDatabase();
            final ContentValues cv = new ContentValues();
            model.fill(cv);
            database.update(DataContract.TABLE_NAME, cv, null, null);

            subject.onNext(getSync());
        });
    }
}
