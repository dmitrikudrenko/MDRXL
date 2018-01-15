package io.github.dmitrikudrenko.core.local;

import android.database.CursorWrapper;
import io.github.dmitrikudrenko.core.local.cursor.GeraltWomanPhotoCursor;

public class Gallery extends CursorWrapper {
    private final int count;
    private final GeraltWomanPhotoCursor cursor;

    public Gallery(final int count, final GeraltWomanPhotoCursor cursor) {
        super(cursor);
        this.count = count;
        this.cursor = cursor;
    }

    public int getCount() {
        return count;
    }

    public GeraltWomanPhotoCursor getCursor() {
        return cursor;
    }
}
