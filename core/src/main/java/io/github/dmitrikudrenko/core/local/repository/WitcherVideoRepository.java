package io.github.dmitrikudrenko.core.local.repository;

import android.content.ContentValues;
import io.github.dmitrikudrenko.core.local.cursor.WitcherVideoCursor;
import io.github.dmitrikudrenko.core.local.database.Database;
import io.github.dmitrikudrenko.core.local.database.contract.WitcherVideoContract;
import io.github.dmitrikudrenko.core.remote.model.video.Video;
import io.github.dmitrikudrenko.core.remote.model.video.Videos;
import rx.Completable;
import rx.Observable;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class WitcherVideoRepository {
    private final Database database;

    @Inject
    public WitcherVideoRepository(final Database database) {
        this.database = database;
    }

    public Observable<WitcherVideoCursor> getVideos() {
        return database.createQuery(WitcherVideoContract.TABLE_NAME, WitcherVideoContract.SELECT_ALL_FOR_BROWSER)
                .map(WitcherVideoCursor::new);
    }

    public Completable updateVideos(@Nullable final Videos videos) {
        if (videos == null) {
            return Completable.complete();
        }
        return Completable.fromAction(() -> {
            final List<ContentValues> batch = new ArrayList<>(videos.size());
            for (final Video video : videos) {
                final ContentValues cv = new ContentValues();
                cv.put(WitcherVideoContract._ID, video.getId());
                cv.put(WitcherVideoContract.COLUMN_URL, video.getUrl());
                cv.put(WitcherVideoContract.COLUMN_NAME, video.getName());
                cv.put(WitcherVideoContract.COLUMN_THUMBNAIL, video.getThumbnail());
                cv.put(WitcherVideoContract.COLUMN_DURATION, video.getDuration());
                batch.add(cv);
            }
            database.insertOrUpdateInTransaction(WitcherVideoContract.TABLE_NAME, batch);
        });
    }

    public Observable<WitcherVideoCursor> getVideo(final long id) {
        return database.createQuery(WitcherVideoContract.TABLE_NAME, WitcherVideoContract.SELECT_BY_ID,
                String.valueOf(id)).map(WitcherVideoCursor::new);
    }
}
