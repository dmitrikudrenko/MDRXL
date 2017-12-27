package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local;

import android.database.Cursor;
import android.database.CursorWrapper;

public class GeraltWomanPhotoCursor extends CursorWrapper {
    private final int id;
    private final int womanId;
    private final int url;

    GeraltWomanPhotoCursor(final Cursor cursor) {
        super(cursor);
        id = cursor.getColumnIndex(GeraltWomenPhotoContract._ID);
        womanId = cursor.getColumnIndex(GeraltWomenPhotoContract.COLUMN_WOMAN_ID);
        url = cursor.getColumnIndex(GeraltWomenPhotoContract.COLUMN_URL);
    }

    public long getId() {
        return getLong(id);
    }

    public long getWomanId() {
        return getLong(womanId);
    }

    public String getUrl() {
        return getString(url);
    }
}
