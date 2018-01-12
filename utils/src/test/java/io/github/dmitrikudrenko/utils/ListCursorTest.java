package io.github.dmitrikudrenko.utils;

import android.database.Cursor;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class ListCursorTest {
    private Cursor cursor;
    private List<Cursor> list;

    @Before
    public void setUp() {
        cursor = mock(Cursor.class);
        list = new ListCursor<>(cursor);
    }

    @Test
    public void shouldMoveTo() {
        when(cursor.moveToPosition(anyInt())).thenReturn(true);

        final int position = 5;
        list.get(position);
        verify(cursor).moveToPosition(position);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowExceptionIfOutOfBounds() {
        when(cursor.moveToPosition(anyInt())).thenReturn(false);
        list.get(0);
    }

    @Test
    public void shouldReturnSize() {
        final int elementsCount = 5;

        when(cursor.getCount()).thenReturn(elementsCount);
        assertEquals(elementsCount, list.size());
    }

    @Test
    public void shouldReturnCursor() {
        assertEquals(cursor, ((ListCursor<Cursor>) list).getCursor());
    }
}
