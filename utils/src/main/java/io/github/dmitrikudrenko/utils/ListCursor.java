package io.github.dmitrikudrenko.utils;

import android.database.Cursor;

import java.util.AbstractList;

public class ListCursor<T extends Cursor> extends AbstractList<T> {
    private final T cursor;

    public ListCursor(final T cursor) {
        this.cursor = cursor;
    }

    @Override
    public T get(final int index) {
        if (cursor.moveToPosition(index)) {
            return cursor;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int size() {
        return cursor.getCount();
    }

    public T getCursor() {
        return cursor;
    }
}
