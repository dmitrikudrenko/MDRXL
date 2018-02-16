package io.github.dmitrikudrenko.core.commands;

import io.github.dmitrikudrenko.core.local.repository.WitcherVideoRepository;
import io.github.dmitrikudrenko.core.remote.WitcherRemoteRepository;
import io.github.dmitrikudrenko.mdrxl.commands.Command;
import rx.Completable;

import javax.inject.Inject;

public class GeraltVideosUpdateCommand implements Command<GeraltVideosUpdateCommandRequest> {
    private final WitcherVideoRepository localRepository;
    private final WitcherRemoteRepository remoteRepository;

    @Inject
    public GeraltVideosUpdateCommand(final WitcherVideoRepository localRepository,
                                     final WitcherRemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    @Override
    public Completable execute(final GeraltVideosUpdateCommandRequest request) {
        return remoteRepository.getVideos()
                .flatMapCompletable(localRepository::updateVideos);
    }
}
