package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman;

import io.github.dmitrikudrenko.mdrxl.sample.model.UpdateModel;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomenRepository;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.remote.GeraltWomenRemoteRepository;
import rx.Completable;
import rx.schedulers.Schedulers;

import javax.inject.Inject;

public final class GeraltWomenStorageCommand {
    private final GeraltWomenRemoteRepository geraltWomenRemoteRepository;
    private final GeraltWomenRepository geraltWomenRepository;

    @Inject
    GeraltWomenStorageCommand(final GeraltWomenRemoteRepository geraltWomenRemoteRepository,
                              final GeraltWomenRepository geraltWomenRepository) {
        this.geraltWomenRemoteRepository = geraltWomenRemoteRepository;
        this.geraltWomenRepository = geraltWomenRepository;
    }

    public Completable save(final UpdateModel model) {
        return geraltWomenRemoteRepository.save(model)
                .flatMapCompletable(success -> {
                    if (success) {
                        return geraltWomenRepository.save(model);
                    }
                    return Completable.complete();
                })
                .subscribeOn(Schedulers.io());
    }
}
