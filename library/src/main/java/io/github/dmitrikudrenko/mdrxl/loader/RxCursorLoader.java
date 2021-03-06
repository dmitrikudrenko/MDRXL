package io.github.dmitrikudrenko.mdrxl.loader;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public abstract class RxCursorLoader<C extends Cursor> extends RxLoader<C> {
    private C mCursor;

    public RxCursorLoader(final Context context) {
        super(context);
    }

    @Override
    void onResult(final C cursor) {
        final C oldCursor = mCursor;
        mCursor = cursor;
        super.onResult(cursor);
        if (oldCursor != null && !oldCursor.isClosed() && oldCursor != cursor) {
            Log.d(getClass().getSimpleName(), "Close old cursor: " + oldCursor.toString());
            oldCursor.close();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
            mCursor = null;
        }
    }
}
