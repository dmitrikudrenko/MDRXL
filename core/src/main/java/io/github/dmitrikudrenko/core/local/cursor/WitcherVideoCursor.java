package io.github.dmitrikudrenko.core.local.cursor;

import android.database.Cursor;
import android.database.CursorWrapper;
import io.github.dmitrikudrenko.core.local.database.contract.WitcherVideoContract;
import io.github.dmitrikudrenko.utils.common.Objects;

public class WitcherVideoCursor extends CursorWrapper {
    private final int id;
    private final int url;
    private final int name;
    private final int duration;
    private final int thumbnail;

    public WitcherVideoCursor(final Cursor cursor) {
        super(cursor);
        id = cursor.getColumnIndex(WitcherVideoContract._ID);
        url = cursor.getColumnIndex(WitcherVideoContract.COLUMN_URL);
        name = cursor.getColumnIndex(WitcherVideoContract.COLUMN_NAME);
        duration = cursor.getColumnIndex(WitcherVideoContract.COLUMN_DURATION);
        thumbnail = cursor.getColumnIndex(WitcherVideoContract.COLUMN_THUMBNAIL);
    }

    public long getId() {
        return getLong(id);
    }

    public String getUrl() {
        return getString(url);
    }

    public String getName() {
        return getString(name);
    }

    public long getDuration() {
        return getLong(duration);
    }

    public String getThumbnail() {
        return getString(thumbnail);
    }

    public boolean areContentsTheSame(final WitcherVideoCursor that) {
        if (Objects.notEquals(getName(), that.getName())) {
            return false;
        }
        if (Objects.notEquals(getDuration(), that.getDuration())) {
            return false;
        }
        if (Objects.notEquals(getThumbnail(), that.getThumbnail())) {
            return false;
        }
        if (Objects.notEquals(getUrl(), that.getUrl())) {
            return false;
        }
        return true;
    }
}
