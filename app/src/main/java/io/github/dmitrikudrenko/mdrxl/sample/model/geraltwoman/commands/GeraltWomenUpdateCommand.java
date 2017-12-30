package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.commands;

import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomenRepository;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.remote.GeraltWomenRemoteRepository;
import rx.Completable;

import javax.inject.Inject;

public class GeraltWomenUpdateCommand {
    private final GeraltWomenRemoteRepository remoteRepository;
    private final GeraltWomenRepository localRepository;

    @Inject
    GeraltWomenUpdateCommand(final GeraltWomenRemoteRepository remoteRepository,
                             final GeraltWomenRepository localRepository) {
        this.remoteRepository = remoteRepository;
        this.localRepository = localRepository;
    }

    public Completable updateAll() {
        return remoteRepository.getWomen()
                .flatMapCompletable(localRepository::updateAll);
    }
}
