package io.github.dmitrikudrenko.mdrxl.sample.model.data;

import io.github.dmitrikudrenko.mdrxl.sample.model.data.local.DataRepository;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.remote.DataRemoteRepository;
import rx.Completable;
import rx.schedulers.Schedulers;

import javax.inject.Inject;

public final class DataStorageCommand {
    private final DataRemoteRepository dataRemoteRepository;
    private final DataRepository dataRepository;

    @Inject
    DataStorageCommand(final DataRemoteRepository dataRemoteRepository,
                       final DataRepository dataRepository) {
        this.dataRemoteRepository = dataRemoteRepository;
        this.dataRepository = dataRepository;
    }

    public Completable save(final UpdateModel model) {
        return dataRemoteRepository.save(model)
                .flatMapCompletable(success -> {
                    if (success) {
                        return dataRepository.save(model);
                    }
                    return Completable.complete();
                })
                .subscribeOn(Schedulers.io());
    }
}
