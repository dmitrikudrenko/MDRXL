package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.contract;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GeraltWomenContract implements Contract {
    private static final String TABLE_NAME = "geralt_women";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHOTO = "photo";
    public static final String COLUMN_PROFESSION = "profession";
    public static final String COLUMN_HAIR_COLOR = "hair_color";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_PHOTO + " TEXT," +
                    COLUMN_PROFESSION + " TEXT," +
                    COLUMN_HAIR_COLOR + " TEXT)";

    private static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final String SELECT_ALL_FOR_BROWSER = "SELECT " +
            _ID + ", " + COLUMN_NAME + ", " + COLUMN_PHOTO + ", " + COLUMN_PROFESSION +
            " FROM " + TABLE_NAME;
    private static final String BY_ID = _ID + "=?";
    private static final String SELECT_BY_ID = SELECT_ALL + " WHERE " + BY_ID;

    private static final String SELECT_WITH_QUERY = SELECT_ALL_FOR_BROWSER + " WHERE " +
            "(" +
            COLUMN_NAME + " LIKE ? OR " +
            COLUMN_PROFESSION + " LIKE ? OR " +
            COLUMN_HAIR_COLOR + " LIKE ? " +
            ")";

    @Inject
    GeraltWomenContract() {
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
        return SELECT_BY_ID;
    }

    public String selectWithQuery() {
        return SELECT_WITH_QUERY;
    }

    public String selectAllForBrowser() {
        return SELECT_ALL_FOR_BROWSER;
    }
}
