package io.github.dmitrikudrenko.core.local.cursor;

import android.database.Cursor;
import android.database.CursorWrapper;
import io.github.dmitrikudrenko.core.local.database.contract.GeraltWomenContract;
import io.github.dmitrikudrenko.utils.common.Objects;

public class GeraltWomenCursor extends CursorWrapper {
    private final int id;
    private final int name;
    private final int photo;
    private final int profession;
    private final int hairColor;
    private final int photoCount;

    public GeraltWomenCursor(final Cursor cursor) {
        super(cursor);
        id = cursor.getColumnIndex(GeraltWomenContract._ID);
        name = cursor.getColumnIndex(GeraltWomenContract.COLUMN_NAME);
        photo = cursor.getColumnIndex(GeraltWomenContract.COLUMN_PHOTO);
        profession = cursor.getColumnIndex(GeraltWomenContract.COLUMN_PROFESSION);
        hairColor = cursor.getColumnIndex(GeraltWomenContract.COLUMN_HAIR_COLOR);
        photoCount = cursor.getColumnIndex(GeraltWomenContract.COLUMN_PHOTO_COUNT);
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

    public int getPhotoCount() {
        return getInt(photoCount);
    }

    public boolean areContentsTheSame(final GeraltWomenCursor that) {
        //use only browser fields (GeraltWomenContract.SELECT_ALL_FOR_BROWSER)
        if (Objects.notEquals(getName(), that.getName())) {
            return false;
        }
        if (Objects.notEquals(getPhoto(), that.getPhoto())) {
            return false;
        }
        if (Objects.notEquals(getProfession(), that.getProfession())) {
            return false;
        }
        return true;
    }
}
