package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import io.github.dmitrikudrenko.mdrxl.sample.model.UpdateModel;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.contract.GeraltWomenContract;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.repository.Database;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.repository.IRepository;
import rx.Completable;
import rx.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class GeraltWomenRepository implements IRepository<GeraltWomenCursor> {
    private final Database database;
    private final GeraltWomenContract contract;

    @Inject
    GeraltWomenRepository(final Database database,
                          final GeraltWomenContract contract) {
        this.database = database;
        this.contract = contract;
    }

    @Override
    public Observable<GeraltWomenCursor> get(final long id) {
        return database.createQuery(contract.tableName(), contract.selectById(), String.valueOf(id))
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
            databaseQuery = database.createQuery(contract.tableName(), contract.selectWithQuery(),
                    likeQuery, likeQuery, likeQuery);
        } else {
            databaseQuery = database.createQuery(contract.tableName(), contract.selectAllForBrowser());
        }
        return databaseQuery.map(GeraltWomenCursor::new);
    }

    public Completable save(final UpdateModel model) {
        return Completable.fromAction(() -> {
            final ContentValues cv = new ContentValues();
            model.fill(cv);
            database.update(contract.tableName(), cv, BaseColumns._ID + "=?",
                    String.valueOf(model.getId()));
        });
    }
}
