package io.github.dmitrikudrenko.mdrxl.sample.model.data.local;

import android.database.Cursor;
import android.database.CursorWrapper;

public class DataCursor extends CursorWrapper {
    private final int id;
    private final int name;
    private final int firstAttribute;
    private final int secondAttribute;
    private final int thirdAttribute;

    public DataCursor(final Cursor cursor) {
        super(cursor);
        id = cursor.getColumnIndex(DataContract._ID);
        name = cursor.getColumnIndex(DataContract.COLUMN_NAME);
        firstAttribute = cursor.getColumnIndex(DataContract.COLUMN_FIRST_ATTRIBUTE);
        secondAttribute = cursor.getColumnIndex(DataContract.COLUMN_SECOND_ATTRIBUTE);
        thirdAttribute = cursor.getColumnIndex(DataContract.COLUMN_THIRD_ATTRIBUTE);
    }

    public int getId() {
        return getInt(id);
    }

    public String getName() {
        return getString(name);
    }

    public String getFirstAttribute() {
        return getString(firstAttribute);
    }

    public String getSecondAttribute() {
        return getString(secondAttribute);
    }

    public String getThirdAttribute() {
        return getString(thirdAttribute);
    }
}
