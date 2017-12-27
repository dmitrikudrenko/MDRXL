package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.contract;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GeraltWomenPhotoContract implements Contract {
    private static final String TABLE_NAME = "photos";

    public static final String COLUMN_WOMAN_ID = "woman_id";
    public static final String COLUMN_URL = "url";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_WOMAN_ID + " INTEGER," +
                    COLUMN_URL + " TEXT)";

    private static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final String BY_WOMAN_ID = COLUMN_WOMAN_ID + "=?";
    private static final String SELECT_BY_WOMAN_ID = SELECT_ALL + " WHERE " + BY_WOMAN_ID;

    @Inject
    GeraltWomenPhotoContract() {
    }

    @Override
    public String tableName() {
        return TABLE_NAME;
    }

    @Override
    public String createTable() {
        return CREATE_TABLE;
    }

    @Override
    public String selectAll() {
        return SELECT_ALL;
    }

    @Override
    public String selectById() {
        return SELECT_BY_WOMAN_ID;
    }
}
