package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local;

import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.repository.Database;
import rx.Observable;

import javax.inject.Inject;

public class GeraltWomanPhotoRepository {
    private final Database database;

    @Inject
    GeraltWomanPhotoRepository(final Database database) {
        this.database = database;
    }

    public Observable<GeraltWomanPhotoCursor> get(final long womanId) {
        return database.createQuery(GeraltWomenContract.TABLE_NAME, GeraltWomenPhotoContract.SELECT_BY_WOMAN_ID, String.valueOf(womanId))
                .map(GeraltWomanPhotoCursor::new);
    }
}
