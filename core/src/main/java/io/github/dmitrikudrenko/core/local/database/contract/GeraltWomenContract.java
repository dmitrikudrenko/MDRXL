package io.github.dmitrikudrenko.core.local.database.contract;

import android.provider.BaseColumns;

public interface GeraltWomenContract extends BaseColumns {
    String TABLE_NAME = "geralt_women";

    String COLUMN_NAME = "name";
    String COLUMN_PHOTO = "photo";
    String COLUMN_PROFESSION = "profession";
    String COLUMN_HAIR_COLOR = "hair_color";
    String COLUMN_PHOTO_COUNT = "photo_count";

    String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_PHOTO + " TEXT," +
                    COLUMN_PROFESSION + " TEXT," +
                    COLUMN_PHOTO_COUNT + " INTEGER," +
                    COLUMN_HAIR_COLOR + " TEXT)";

    String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    String SELECT_ALL_FOR_BROWSER = "SELECT " +
            _ID + ", " + COLUMN_NAME + ", " + COLUMN_PHOTO + ", " + COLUMN_PROFESSION +
            " FROM " + TABLE_NAME;
    String BY_ID = _ID + "=?";
    String SELECT_BY_ID = SELECT_ALL + " WHERE " + BY_ID;

    String SELECT_WITH_QUERY = SELECT_ALL_FOR_BROWSER + " WHERE " +
            "(" +
            COLUMN_NAME + " LIKE ? OR " +
            COLUMN_PROFESSION + " LIKE ? OR " +
            COLUMN_HAIR_COLOR + " LIKE ? " +
            ")";
}
