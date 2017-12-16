package io.github.dmitrikudrenko.mdrxl.sample.model.data.local.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.Nullable;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.local.DataSqliteOpenHelper;
import rx.Observable;
import rx.schedulers.Schedulers;

import javax.inject.Inject;

public class Database {
    private final BriteDatabase briteDatabase;

    @Inject
    Database(final DataSqliteOpenHelper helper) {
        final SqlBrite sqlBrite = new SqlBrite.Builder().build();
        this.briteDatabase = sqlBrite.wrapDatabaseHelper(helper, Schedulers.io());
    }

    public Observable<Cursor> createQuery(final String table, final String sql, final String... args) {
        return briteDatabase.createQuery(table, sql, args)
                .map(SqlBrite.Query::run);
    }

    public int update(final String table, final ContentValues values,
                      @Nullable final String whereClause, @Nullable final String... whereArgs) {
        return briteDatabase.update(table, values, whereClause, whereArgs);
    }
}
