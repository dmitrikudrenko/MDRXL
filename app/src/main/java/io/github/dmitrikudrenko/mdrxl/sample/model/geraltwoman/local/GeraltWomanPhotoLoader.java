package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local;

import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import io.github.dmitrikudrenko.mdrxl.loader.RxCursorLoader;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.contract.GeraltWomenPhotoContract;
import rx.Observable;

@AutoFactory
public class GeraltWomanPhotoLoader extends RxCursorLoader<GeraltWomanPhotoCursor> {
    private final GeraltWomanPhotoRepository repository;
    private final GeraltWomenRepository womenRepository;
    private final long id;

    GeraltWomanPhotoLoader(@Provided final Context context,
                           @Provided final GeraltWomanPhotoRepository repository,
                           @Provided final GeraltWomenRepository womenRepository,
                           final long id) {
        super(context);
        this.repository = repository;
        this.womenRepository = womenRepository;
        this.id = id;
    }

    @Override
    protected Observable<GeraltWomanPhotoCursor> create(final String query) {
        final Observable<GeraltWomanPhotoCursor> avatarObservable = womenRepository.get(id)
                .map(GeraltWomanPhotoCursorFromWoman::new);

        final Observable<GeraltWomanPhotoCursor> photosObservable = repository.get(id);

        return Observable.combineLatest(photosObservable, avatarObservable,
                (photos, avatar) -> new GeraltWomanPhotoCursor(new MergeCursor(new Cursor[]{avatar, photos})));
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
