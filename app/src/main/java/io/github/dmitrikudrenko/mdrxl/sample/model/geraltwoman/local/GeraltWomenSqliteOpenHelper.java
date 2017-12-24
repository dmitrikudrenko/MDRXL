package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class GeraltWomenSqliteOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "GeraltWoman.db";
    private static final int DATABASE_VERSION = 1;

    @Inject
    GeraltWomenSqliteOpenHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL(GeraltWomenContract.CREATE_TABLE);

        createStubData(db);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {

    }

    private void createStubData(final SQLiteDatabase db) {
        createTrissMerigold(db);
        createYennefer(db);
        createCiri(db);
        createShani(db);
        createSyanna(db);
        createPassifloraProstitute(db);
    }

    private void createTrissMerigold(final SQLiteDatabase db) {
        createGeraltWoman(db, 0, "Triss Merigold", null,
                "Witch", "Redhead");
    }

    private void createYennefer(final SQLiteDatabase db) {
        createGeraltWoman(db, 1, "Yennefer from Vengerberg", null,
                "Witch", "Black");
    }

    private void createCiri(final SQLiteDatabase db) {
        createGeraltWoman(db, 2, "Cirilla Fiona Elen Riannon", null,
                "Princess of Cintra", "Silver");
    }

    private void createShani(final SQLiteDatabase db) {
        createGeraltWoman(db, 3, "Shani", null, "Medic", "Redhead");
    }

    private void createSyanna(final SQLiteDatabase db) {
        createGeraltWoman(db, 4, "Sylvia Anna", null,
                "Anna Henrietta's sister", "Black");
    }

    private void createPassifloraProstitute(final SQLiteDatabase db) {
        createGeraltWoman(db, 5, "Viola", null, "Prostitute", "Blonde");
    }

    private void createGeraltWoman(final SQLiteDatabase db, final long id, final String name,
                                   final String photo, final String profession, final String hairColor) {
        final ContentValues cv = new ContentValues();

        cv.put(GeraltWomenContract._ID, id);
        cv.put(GeraltWomenContract.COLUMN_NAME, name);
        cv.put(GeraltWomenContract.COLUMN_PHOTO, photo);
        cv.put(GeraltWomenContract.COLUMN_PROFESSION, profession);
        cv.put(GeraltWomenContract.COLUMN_HAIR_COLOR, hairColor);

        db.insert(GeraltWomenContract.TABLE_NAME, null, cv);
    }
}
