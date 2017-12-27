package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local;

import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.contract.GeraltWomenContract;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.repository.Database;
import rx.Observable;

import javax.inject.Inject;

public class GeraltWomanPhotoRepository {
    private final Database database;
    private final GeraltWomenContract contract;

    @Inject
    GeraltWomanPhotoRepository(final Database database,
                               final GeraltWomenContract contract) {
        this.database = database;
        this.contract = contract;
    }

    public Observable<GeraltWomanPhotoCursor> get(final long womanId) {
        return database.createQuery(contract.tableName(), contract.selectById(), String.valueOf(womanId))
                .map(GeraltWomanPhotoCursor::new);
    }
}
