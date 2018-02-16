package io.github.dmitrikudrenko.core.local.loader.women;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import io.github.dmitrikudrenko.core.local.Gallery;
import io.github.dmitrikudrenko.core.local.cursor.GeraltWomanPhotoCursor;
import io.github.dmitrikudrenko.core.local.cursor.GeraltWomenCursor;
import io.github.dmitrikudrenko.core.local.database.contract.GeraltWomenPhotoContract;
import io.github.dmitrikudrenko.core.local.repository.GeraltWomenRepository;
import io.github.dmitrikudrenko.mdrxl.loader.RxCursorLoader;
import rx.Observable;

@AutoFactory
public class GeraltWomanPhotoLoader extends RxCursorLoader<Gallery> {
    private final GeraltWomenRepository repository;
    private final GeraltWomenRepository womenRepository;
    private final long id;

    GeraltWomanPhotoLoader(@Provided final Context context,
                           @Provided final GeraltWomenRepository repository,
                           @Provided final GeraltWomenRepository womenRepository,
                           final long id) {
        super(context);
        this.repository = repository;
        this.womenRepository = womenRepository;
        this.id = id;
    }

    @Override
    protected Observable<Gallery> create(final String query) {
        final Observable<GeraltWomenCursor> avatarObservable = womenRepository.getWoman(id);
        final Observable<GeraltWomanPhotoCursor> photosObservable = repository.getPhotos(id);

        return Observable.combineLatest(avatarObservable, photosObservable, (womenCursor, photos) -> {
            final GeraltWomanPhotoCursor avatar = new GeraltWomanPhotoCursorFromWoman(womenCursor);
            womenCursor.moveToFirst();
            return new Gallery(womenCursor.getPhotoCount() + 1,
                    new GeraltWomanPhotoCursor(new MergeCursor(new Cursor[]{avatar, photos})));
        });
    }

    private static class GeraltWomanPhotoCursorFromWoman extends GeraltWomanPhotoCursor {

        GeraltWomanPhotoCursorFromWoman(final Cursor cursor) {
            super(cursor);
        }

        @Override
        public String[] getColumnNames() {
            return new String[]{
                    GeraltWomenPhotoContract._ID,
                    GeraltWomenPhotoContract.COLUMN_WOMAN_ID,
                    GeraltWomenPhotoContract.COLUMN_URL
            };
        }

        @Override
        public long getId() {
            return -1;
        }

        @Override
        public long getWomanId() {
            return getWrappedCursor().getId();
        }

        @Override
        public String getUrl() {
            return getWrappedCursor().getPhoto();
        }

        @Override
        public GeraltWomenCursor getWrappedCursor() {
            return (GeraltWomenCursor) super.getWrappedCursor();
        }
    }
}
