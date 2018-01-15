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

    private final GeraltWomenContract womenContract;
    private final GeraltWomenPhotoContract womenPhotoContract;

    @Inject
    GeraltWomenSqliteOpenHelper(final Context context,
                                final GeraltWomenContract womenContract,
                                final GeraltWomenPhotoContract womenPhotoContract) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.womenContract = womenContract;
        this.womenPhotoContract = womenPhotoContract;
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL(womenContract.createTable());
        db.execSQL(womenPhotoContract.createTable());
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {

    }
}
