package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import io.github.dmitrikudrenko.mdrxl.sample.model.UpdateModel;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.contract.GeraltWomenContract;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.repository.Database;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.repository.IRepository;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.remote.model.Woman;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.remote.model.Women;
import rx.Completable;
import rx.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

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

    public Completable updateAll(final Women women) {
        return Completable.fromAction(() -> {
            final List<ContentValues> batch = new ArrayList<>(women.size());
            for (final Woman woman : women) {
                final ContentValues cv = new ContentValues();
                cv.put(GeraltWomenContract._ID, woman.getId());
                cv.put(GeraltWomenContract.COLUMN_NAME, woman.getName());
                cv.put(GeraltWomenContract.COLUMN_PROFESSION, woman.getProfession());
                cv.put(GeraltWomenContract.COLUMN_HAIR_COLOR, woman.getHairColor());
                cv.put(GeraltWomenContract.COLUMN_PHOTO, woman.getPhoto());
                batch.add(cv);
            }
            database.insertOrUpdateInTransaction(contract.tableName(), batch);
        });
    }
}
