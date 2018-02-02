package io.github.dmitrikudrenko.core.commands;

import io.github.dmitrikudrenko.core.local.repository.GeraltWomenRepository;
import io.github.dmitrikudrenko.core.remote.WitcherRemoteRepository;
import io.github.dmitrikudrenko.mdrxl.commands.Command;
import rx.Completable;

import javax.inject.Inject;

public class GeraltWomanPhotosUpdateCommand implements Command<GeraltWomanPhotosUpdateCommandRequest> {
    private final WitcherRemoteRepository remoteRepository;
    private final GeraltWomenRepository localRepository;

    @Inject
    GeraltWomanPhotosUpdateCommand(final WitcherRemoteRepository remoteRepository,
                                   final GeraltWomenRepository localRepository) {
        this.remoteRepository = remoteRepository;
        this.localRepository = localRepository;
    }

    @Override
    public Completable execute(final GeraltWomanPhotosUpdateCommandRequest request) {
        final long womanId = request.getWomanId();
        return remoteRepository.getPhotos(womanId)
                .flatMapCompletable(photos -> localRepository.updatePhotos(photos, womanId));
    }
}
