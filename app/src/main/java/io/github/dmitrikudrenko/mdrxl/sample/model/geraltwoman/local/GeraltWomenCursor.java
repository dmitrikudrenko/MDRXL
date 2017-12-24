package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local;

import android.database.Cursor;
import android.database.CursorWrapper;

public final class GeraltWomenCursor extends CursorWrapper {
    private final int id;
    private final int name;
    private final int photo;
    private final int profession;
    private final int hairColor;

    GeraltWomenCursor(final Cursor cursor) {
        super(cursor);
        id = cursor.getColumnIndex(GeraltWomenContract._ID);
        name = cursor.getColumnIndex(GeraltWomenContract.COLUMN_NAME);
        photo = cursor.getColumnIndex(GeraltWomenContract.COLUMN_PHOTO);
        profession = cursor.getColumnIndex(GeraltWomenContract.COLUMN_PROFESSION);
        hairColor = cursor.getColumnIndex(GeraltWomenContract.COLUMN_HAIR_COLOR);
    }

    public int getId() {
        return getInt(id);
    }

    public String getName() {
        return getString(name);
    }

    public String getPhoto() {
        return getString(photo);
    }

    public String getProfession() {
        return getString(profession);
    }

    public String getHairColor() {
        return getString(hairColor);
    }
}
