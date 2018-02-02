package io.github.dmitrikudrenko.core.local.database.contract;

import android.provider.BaseColumns;

public interface WitcherVideoContract extends BaseColumns {
    String TABLE_NAME = "geralt_video";

    String COLUMN_URL = "url";
    String COLUMN_NAME = "name";
    String COLUMN_DURATION = "duration";
    String COLUMN_THUMBNAIL = "thumbnail";

    String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_DURATION + " INTEGER," +
                    COLUMN_THUMBNAIL + " TEXT," +
                    COLUMN_URL + " TEXT)";

    String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    String SELECT_ALL_FOR_BROWSER = "SELECT " +
            _ID + ", " +
            COLUMN_NAME + ", " +
            COLUMN_DURATION + ", " +
            COLUMN_THUMBNAIL + ", " +
            COLUMN_URL +
            " FROM " + TABLE_NAME;
    String BY_ID = _ID + "=?";
    String SELECT_BY_ID = SELECT_ALL + " WHERE " + BY_ID;
}
