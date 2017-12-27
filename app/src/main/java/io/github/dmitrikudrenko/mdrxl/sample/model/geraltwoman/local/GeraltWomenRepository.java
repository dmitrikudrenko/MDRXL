package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local;

import android.content.ContentValues;
import android.database.Cursor;
import io.github.dmitrikudrenko.mdrxl.sample.model.UpdateModel;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.repository.Database;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.repository.IRepository;
import rx.Completable;
import rx.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class GeraltWomenRepository implements IRepository<GeraltWomenCursor> {
    private final Database database;

    @Inject
    GeraltWomenRepository(final Database database) {
        this.database = database;
    }

    @Override
    public Observable<GeraltWomenCursor> get(final long id) {
        return database.createQuery(GeraltWomenContract.TABLE_NAME, GeraltWomenContract.SELECT_BY_ID, String.valueOf(id))
                .map(GeraltWomenCursor::new);
    }

    @Override
    public Observable<GeraltWomenCursor> get() {
        return get(null);
    }

    @Override
    public Observable<GeraltWomenCursor> get(final String query) {
        final Observable<Cursor> databaseQuery;
        if (query != null) {
            final String likeQuery = "%" + query + "%";
            databaseQuery = database.createQuery(GeraltWomenContract.TABLE_NAME, GeraltWomenContract.SELECT_WITH_QUERY,
                    likeQuery, likeQuery, likeQuery);
        } else {
            databaseQuery = database.createQuery(GeraltWomenContract.TABLE_NAME, GeraltWomenContract.SELECT_ALL_FOR_BROWSER);
        }
        return databaseQuery.map(GeraltWomenCursor::new);
    }

    public Completable save(final UpdateModel model) {
        return Completable.fromAction(() -> {
            final ContentValues cv = new ContentValues();
            model.fill(cv);
            database.update(GeraltWomenContract.TABLE_NAME, cv, GeraltWomenContract.BY_ID,
                    String.valueOf(model.getId()));
        });
    }
}
