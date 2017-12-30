package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.commands;

import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomanPhotoRepository;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.remote.GeraltWomenRemoteRepository;
import rx.Completable;

import javax.inject.Inject;

public class GeraltWomanPhotosUpdateCommand {
    private final GeraltWomenRemoteRepository remoteRepository;
    private final GeraltWomanPhotoRepository localRepository;

    @Inject
    GeraltWomanPhotosUpdateCommand(final GeraltWomenRemoteRepository remoteRepository,
                                          final GeraltWomanPhotoRepository localRepository) {
        this.remoteRepository = remoteRepository;
        this.localRepository = localRepository;
    }

    public Completable updatePhotos(final long womanId) {
        return remoteRepository.getPhotos(womanId)
                .flatMapCompletable(photos -> localRepository.updatePhotos(photos, womanId));
    }
}
