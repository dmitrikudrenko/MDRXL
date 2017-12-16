package io.github.dmitrikudrenko.mdrxl.loader;

import android.content.Context;
import android.database.Cursor;

public abstract class RxCursorLoader<C extends Cursor> extends RxLoader<C> {
    private C mCursor;

    public RxCursorLoader(final Context context) {
        super(context);
    }

    @Override
    void onResult(final C cursor) {
        mCursor = cursor;
        super.onResult(cursor);
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
