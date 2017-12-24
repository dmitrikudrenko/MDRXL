package io.github.dmitrikudrenko.mdrxl.sample.model.data.local;

import android.provider.BaseColumns;

public interface DataContract extends BaseColumns {
    String TABLE_NAME = "data_model";

    String COLUMN_NAME = "name";
    String COLUMN_FIRST_ATTRIBUTE = "first_attribute";
    String COLUMN_SECOND_ATTRIBUTE = "second_attribute";
    String COLUMN_THIRD_ATTRIBUTE = "third_attribute";

    String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_FIRST_ATTRIBUTE + " TEXT," +
                    COLUMN_SECOND_ATTRIBUTE + " TEXT," +
                    COLUMN_THIRD_ATTRIBUTE + " TEXT)";

    String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    String BY_ID = _ID + "=?";
    String SELECT_BY_ID = SELECT_ALL + " WHERE " + BY_ID;
    String SELECT_WITH_QUERY = SELECT_ALL + " WHERE " + COLUMN_NAME + " LIKE ?";
}
