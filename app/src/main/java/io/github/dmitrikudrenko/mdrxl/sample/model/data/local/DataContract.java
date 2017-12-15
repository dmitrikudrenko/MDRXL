package io.github.dmitrikudrenko.mdrxl.sample.model.data.local;

import android.provider.BaseColumns;

public interface DataContract extends BaseColumns {
    String TABLE_NAME = "data_model";

    String COLUMN_NAME = "name";
    String COLUMN_FIRST_ATTRIBUTE = "first_attribute";
    String COLUMN_SECOND_ATTRIBUTE = "second_attribute";
    String COLUMN_THIRD_ATTRIBUTE = "third_attribute";

    String[] PROJECTION = {
            _ID,
            COLUMN_NAME,
            COLUMN_FIRST_ATTRIBUTE,
            COLUMN_SECOND_ATTRIBUTE,
            COLUMN_THIRD_ATTRIBUTE
    };

    String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_FIRST_ATTRIBUTE + " TEXT," +
                    COLUMN_SECOND_ATTRIBUTE + " TEXT," +
                    COLUMN_THIRD_ATTRIBUTE + " TEXT)";
}
