package io.github.dmitrikudrenko.mdrxl.sample.model.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataSqliteOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Data.db";
    private static final int DATABASE_VERSION = 1;

    @Inject
    DataSqliteOpenHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL(DataContract.CREATE_TABLE);

        createStubData(db);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {

    }

    private void createStubData(final SQLiteDatabase db) {
        for (int i = 0; i < 5; i++) {
            final ContentValues cv = new ContentValues();

            cv.put(DataContract._ID, i);
            cv.put(DataContract.COLUMN_NAME, "Name #" + i);
            cv.put(DataContract.COLUMN_FIRST_ATTRIBUTE, "First attribute #" + i);
            cv.put(DataContract.COLUMN_SECOND_ATTRIBUTE, "Second attribute #" + i);
            cv.put(DataContract.COLUMN_THIRD_ATTRIBUTE, "Third attribute #" + i);

            db.insert(DataContract.TABLE_NAME, null, cv);
        }
    }
}
