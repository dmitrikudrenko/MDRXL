package io.github.dmitrikudrenko.mdrxl.sample.model.data;

import io.github.dmitrikudrenko.mdrxl.sample.model.data.local.DataRepository;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.remote.DataRemoteRepository;
import rx.Completable;
import rx.schedulers.Schedulers;

import javax.inject.Inject;

public class DataStorageCommand {
    private final DataRemoteRepository dataRemoteRepository;
    private final DataRepository dataRepository;

    @Inject
    DataStorageCommand(final DataRemoteRepository dataRemoteRepository,
                       final DataRepository dataRepository) {
        this.dataRemoteRepository = dataRemoteRepository;
        this.dataRepository = dataRepository;
    }

    public Completable save(final Data data) {
        return dataRemoteRepository.save(data)
                .flatMap(dataRepository::save)
                .toCompletable()
                .subscribeOn(Schedulers.io());
    }
}
