package io.github.dmitrikudrenko.core.local.database.contract;

import android.provider.BaseColumns;

public interface GeraltWomenPhotoContract extends BaseColumns {
    String TABLE_NAME = "photos";

    String COLUMN_WOMAN_ID = "woman_id";
    String COLUMN_URL = "url";

    String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_WOMAN_ID + " INTEGER," +
                    COLUMN_URL + " TEXT)";

    String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    String BY_WOMAN_ID = COLUMN_WOMAN_ID + "=?";
    String SELECT_BY_WOMAN_ID = SELECT_ALL + " WHERE " + BY_WOMAN_ID;
}
