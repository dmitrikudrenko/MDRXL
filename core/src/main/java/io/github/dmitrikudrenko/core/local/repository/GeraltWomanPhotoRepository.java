package io.github.dmitrikudrenko.core.local.repository;

import android.content.ContentValues;
import io.github.dmitrikudrenko.core.local.cursor.GeraltWomanPhotoCursor;
import io.github.dmitrikudrenko.core.local.database.Database;
import io.github.dmitrikudrenko.core.local.database.contract.GeraltWomenPhotoContract;
import io.github.dmitrikudrenko.core.remote.model.Photo;
import io.github.dmitrikudrenko.core.remote.model.Photos;
import rx.Completable;
import rx.Observable;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class GeraltWomanPhotoRepository {
    private final Database database;
    private final GeraltWomenPhotoContract contract;

    @Inject
    GeraltWomanPhotoRepository(final Database database,
                               final GeraltWomenPhotoContract contract) {
        this.database = database;
        this.contract = contract;
    }

    public Observable<GeraltWomanPhotoCursor> get(final long womanId) {
        return database.createQuery(contract.tableName(), contract.selectById(), String.valueOf(womanId))
                .map(GeraltWomanPhotoCursor::new);
    }

    public Completable updatePhotos(@Nullable final Photos photos, final long womanId) {
        if (photos == null) {
            return Completable.complete();
        }
        return Completable.fromAction(() -> {
            final List<ContentValues> batch = new ArrayList<>(photos.size());
            for (final Photo photo : photos) {
                if (photo == null) {
                    //firebase returns 'null' as first item
                    continue;
                }
                final ContentValues cv = new ContentValues();
                cv.put(GeraltWomenPhotoContract._ID, photo.getId());
                cv.put(GeraltWomenPhotoContract.COLUMN_URL, photo.getUrl());
                cv.put(GeraltWomenPhotoContract.COLUMN_WOMAN_ID, womanId);
                batch.add(cv);
            }
            database.insertOrUpdateInTransaction(contract.tableName(), batch);
        });
    }
}