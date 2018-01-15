package io.github.dmitrikudrenko.core.commands;

import io.github.dmitrikudrenko.core.local.repository.GeraltWomenRepository;
import io.github.dmitrikudrenko.core.remote.GeraltWomenRemoteRepository;
import io.github.dmitrikudrenko.mdrxl.commands.Command;
import rx.Completable;

import javax.inject.Inject;

public class GeraltWomenUpdateCommand implements Command<GeraltWomenUpdateCommandRequest> {
    private final GeraltWomenRemoteRepository remoteRepository;
    private final GeraltWomenRepository localRepository;

    @Inject
    GeraltWomenUpdateCommand(final GeraltWomenRemoteRepository remoteRepository,
                             final GeraltWomenRepository localRepository) {
        this.remoteRepository = remoteRepository;
        this.localRepository = localRepository;
    }

    @Override
    public Completable execute(final GeraltWomenUpdateCommandRequest request) {
        return remoteRepository.getWomen()
                .flatMapCompletable(localRepository::updateAll);
    }
}
