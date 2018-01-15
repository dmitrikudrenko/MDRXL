package io.github.dmitrikudrenko.core.commands;

import io.github.dmitrikudrenko.core.local.repository.GeraltWomenRepository;
import io.github.dmitrikudrenko.core.remote.GeraltWomenRemoteRepository;
import io.github.dmitrikudrenko.mdrxl.commands.Command;
import rx.Completable;

import javax.inject.Inject;

public class GeraltWomanUpdateCommand implements Command<GeraltWomanUpdateCommandRequest> {
    private final GeraltWomenRemoteRepository remoteRepository;
    private final GeraltWomenRepository localRepository;

    @Inject
    public GeraltWomanUpdateCommand(final GeraltWomenRemoteRepository remoteRepository,
                                    final GeraltWomenRepository localRepository) {
        this.remoteRepository = remoteRepository;
        this.localRepository = localRepository;
    }

    @Override
    public Completable execute(final GeraltWomanUpdateCommandRequest request) {
        return remoteRepository.getWoman(request.getId())
                .flatMapCompletable(localRepository::update);
    }
}
