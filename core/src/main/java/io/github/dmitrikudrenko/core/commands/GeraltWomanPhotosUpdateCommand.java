package io.github.dmitrikudrenko.core.commands;

import io.github.dmitrikudrenko.core.local.repository.GeraltWomanPhotoRepository;
import io.github.dmitrikudrenko.core.remote.GeraltWomenRemoteRepository;
import io.github.dmitrikudrenko.mdrxl.commands.Command;
import rx.Completable;

import javax.inject.Inject;

public class GeraltWomanPhotosUpdateCommand implements Command<GeraltWomanPhotosUpdateCommandRequest> {
    private final GeraltWomenRemoteRepository remoteRepository;
    private final GeraltWomanPhotoRepository localRepository;

    @Inject
    GeraltWomanPhotosUpdateCommand(final GeraltWomenRemoteRepository remoteRepository,
                                   final GeraltWomanPhotoRepository localRepository) {
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
