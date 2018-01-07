package io.github.dmitrikudrenko.mdrxl.loader;

import android.database.Cursor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import rx.Observable;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class RxCursorLoaderTest {
    private RxCursorLoader<Cursor> rxCursorLoader;
    private Cursor cursor;

    @Before
    public void setUp() {
        rxCursorLoader = new RxCursorLoader<Cursor>(RuntimeEnvironment.systemContext) {
            @Override
            protected Observable<Cursor> create(final String query) {
                return null;
            }
        };
        cursor = mock(Cursor.class);
    }

    private void onResult() {
        rxCursorLoader.onResult(cursor);
    }

    @Test
    public void shouldCloseCurrentCursor() {
        onResult();
        rxCursorLoader.onReset();

        verify(cursor).close();
    }

    @Test
    public void shouldCloseNothingOnReset() {
        rxCursorLoader.onReset();
        verify(cursor, never()).close();
    }

    @Test
    public void shouldCloseNothingOnResult() {
        onResult();
        verify(cursor, never()).close();
    }

    @Test
    public void shouldNotCloseOnResultIfAlreadyClosed() {
        when(cursor.isClosed()).thenReturn(true);
        onResult();
        rxCursorLoader.onReset();
        verify(cursor, never()).close();
    }

    @Test
    public void shouldCloseOldCursor() {
        onResult();
        rxCursorLoader.onResult(mock(Cursor.class));

        verify(cursor).close();
    }

    @Test
    public void shouldNotCloseOldCursorIfAlreadyClosed() {
        when(cursor.isClosed()).thenReturn(true);
        onResult();
        rxCursorLoader.onResult(mock(Cursor.class));

        verify(cursor, never()).close();
    }

    @Test
    public void shouldCloseOldCursorBecauseItHasNotChanged() {
        onResult();
        rxCursorLoader.onResult(cursor);

        verify(cursor, never()).close();
    }
}
