package io.github.dmitrikudrenko.mdrxl.sample.model.data.local;

import android.content.ContentValues;
import android.database.Cursor;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.UpdateModel;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.local.repository.Database;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.local.repository.IRepository;
import rx.Completable;
import rx.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class DataRepository implements IRepository<DataCursor> {
    private final Database database;

    @Inject
    DataRepository(final Database database) {
        this.database = database;
    }

    @Override
    public Observable<DataCursor> get(final long id) {
        return database.createQuery(DataContract.TABLE_NAME, DataContract.SELECT_BY_ID, String.valueOf(id))
                .map(DataCursor::new);
    }

    @Override
    public Observable<DataCursor> get() {
        return get(null);
    }

    @Override
    public Observable<DataCursor> get(final String query) {
        final Observable<Cursor> databaseQuery;
        if (query != null) {
            databaseQuery = database.createQuery(DataContract.TABLE_NAME, DataContract.SELECT_WITH_QUERY,
                    "%" + query + "%");
        } else {
            databaseQuery = database.createQuery(DataContract.TABLE_NAME, DataContract.SELECT_ALL);
        }
        return databaseQuery.map(DataCursor::new);
    }

    public Completable save(final UpdateModel model) {
        return Completable.fromAction(() -> {
            final ContentValues cv = new ContentValues();
            model.fill(cv);
            database.update(DataContract.TABLE_NAME, cv, DataContract.BY_ID,
                    String.valueOf(model.getId()));
        });
    }
}
