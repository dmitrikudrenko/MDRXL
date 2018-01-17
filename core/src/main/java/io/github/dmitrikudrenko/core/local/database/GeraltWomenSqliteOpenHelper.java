package io.github.dmitrikudrenko.core.local.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import io.github.dmitrikudrenko.core.local.database.contract.GeraltWomenContract;
import io.github.dmitrikudrenko.core.local.database.contract.GeraltWomenPhotoContract;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class GeraltWomenSqliteOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "GeraltWoman.db";
    private static final int DATABASE_VERSION = 1;

    @Inject
    public GeraltWomenSqliteOpenHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL(GeraltWomenContract.CREATE_TABLE);
        db.execSQL(GeraltWomenPhotoContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {

    }
}
