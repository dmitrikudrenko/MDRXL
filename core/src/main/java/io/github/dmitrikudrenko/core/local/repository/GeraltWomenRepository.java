package io.github.dmitrikudrenko.core.local.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import io.github.dmitrikudrenko.core.local.cursor.GeraltWomanPhotoCursor;
import io.github.dmitrikudrenko.core.local.cursor.GeraltWomenCursor;
import io.github.dmitrikudrenko.core.local.database.Database;
import io.github.dmitrikudrenko.core.local.database.contract.GeraltWomenContract;
import io.github.dmitrikudrenko.core.local.database.contract.GeraltWomenPhotoContract;
import io.github.dmitrikudrenko.core.remote.UpdateModel;
import io.github.dmitrikudrenko.core.remote.model.Photo;
import io.github.dmitrikudrenko.core.remote.model.Photos;
import io.github.dmitrikudrenko.core.remote.model.Woman;
import io.github.dmitrikudrenko.core.remote.model.Women;
import rx.Completable;
import rx.Observable;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class GeraltWomenRepository {
    private final Database database;

    @Inject
    public GeraltWomenRepository(final Database database) {
        this.database = database;
    }

    public Observable<GeraltWomenCursor> getWoman(final long id) {
        return database.createQuery(GeraltWomenContract.TABLE_NAME, GeraltWomenContract.SELECT_BY_ID, String.valueOf(id))
                .map(GeraltWomenCursor::new);
    }

    public Observable<GeraltWomenCursor> getWomen() {
        return getWomen(null);
    }

    public Observable<GeraltWomenCursor> getWomen(final String query) {
        final Observable<Cursor> databaseQuery;
        if (query != null) {
            final String likeQuery = "%" + query + "%";
            databaseQuery = database.createQuery(GeraltWomenContract.TABLE_NAME, GeraltWomenContract.SELECT_WITH_QUERY,
                    likeQuery, likeQuery, likeQuery);
        } else {
            databaseQuery = database.createQuery(GeraltWomenContract.TABLE_NAME, GeraltWomenContract.SELECT_ALL_FOR_BROWSER);
        }
        return databaseQuery.map(GeraltWomenCursor::new);
    }

    public Completable save(final UpdateModel model) {
        return Completable.fromAction(() -> {
            final ContentValues cv = new ContentValues();
            model.fill(cv);
            database.update(GeraltWomenContract.TABLE_NAME, cv, BaseColumns._ID + "=?",
                    String.valueOf(model.getId()));
        });
    }

    public Completable updateAll(@Nullable final Women women) {
        if (women == null) {
            return Completable.complete();
        }
        return Completable.fromAction(() -> {
            final List<ContentValues> batch = new ArrayList<>(women.size());
            for (final Woman woman : women) {
                final ContentValues cv = womanToContentValues(woman);
                batch.add(cv);
            }
            database.insertOrUpdateInTransaction(GeraltWomenContract.TABLE_NAME, batch);
        });
    }

    @NonNull
    private ContentValues womanToContentValues(final Woman woman) {
        final ContentValues cv = new ContentValues();
        cv.put(GeraltWomenContract._ID, woman.getId());
        cv.put(GeraltWomenContract.COLUMN_NAME, woman.getName());
        cv.put(GeraltWomenContract.COLUMN_PROFESSION, woman.getProfession());
        cv.put(GeraltWomenContract.COLUMN_HAIR_COLOR, woman.getHairColor());
        cv.put(GeraltWomenContract.COLUMN_PHOTO, woman.getPhoto());
        cv.put(GeraltWomenContract.COLUMN_PHOTO_COUNT, woman.getPhotoCount());
        return cv;
    }

    public Completable update(@Nullable final Woman woman) {
        if (woman == null) {
            return Completable.complete();
        }
        return Completable.fromAction(() -> database.update(GeraltWomenContract.TABLE_NAME,
                womanToContentValues(woman), GeraltWomenContract.BY_ID, String.valueOf(woman.getId())));
    }

    public Observable<GeraltWomanPhotoCursor> getPhotos(final long womanId) {
        return database.createQuery(GeraltWomenPhotoContract.TABLE_NAME,
                GeraltWomenPhotoContract.SELECT_BY_WOMAN_ID, String.valueOf(womanId))
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
            database.insertOrUpdateInTransaction(GeraltWomenPhotoContract.TABLE_NAME, batch);
        });
    }
}
