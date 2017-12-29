package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local;

import android.database.CursorWrapper;

public class Gallery extends CursorWrapper {
    private final int count;
    private final GeraltWomanPhotoCursor cursor;

    Gallery(final int count, final GeraltWomanPhotoCursor cursor) {
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
