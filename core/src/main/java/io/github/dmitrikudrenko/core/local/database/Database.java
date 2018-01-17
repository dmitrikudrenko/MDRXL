package io.github.dmitrikudrenko.core.local.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import rx.Observable;
import rx.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.List;

public class Database {
    private final BriteDatabase briteDatabase;

    @Inject
    public Database(final GeraltWomenSqliteOpenHelper helper) {
        final SqlBrite sqlBrite = new SqlBrite.Builder().build();
        this.briteDatabase = sqlBrite.wrapDatabaseHelper(helper, Schedulers.io());
    }

    public Observable<Cursor> createQuery(final String table, final String sql, final String... args) {
        return briteDatabase.createQuery(table, sql, args)
                .map(SqlBrite.Query::run);
    }

    public long insertOrUpdate(final String table, final ContentValues values) {
        return briteDatabase.insert(table, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public int update(final String table, final ContentValues values,
                      @Nullable final String whereClause, @Nullable final String... whereArgs) {
        final int updated = briteDatabase.update(table, values, whereClause, whereArgs);
        if (updated == 0) {
            briteDatabase.insert(table, values);
        }
        return updated;
    }

    public int insertOrUpdateInTransaction(final String table, final List<ContentValues> batch) {
        final BriteDatabase.Transaction transaction = briteDatabase.newTransaction();
        int updatedRows = 0;
        try {
            for (final ContentValues cv : batch) {
                updatedRows += (insertOrUpdate(table, cv) >= 0 ? 1 : 0);
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
        return updatedRows;
    }
}
