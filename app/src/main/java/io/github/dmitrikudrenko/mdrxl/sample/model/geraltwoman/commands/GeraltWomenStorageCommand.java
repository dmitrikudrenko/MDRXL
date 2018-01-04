package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.commands;

import io.github.dmitrikudrenko.mdrxl.commands.Command;
import io.github.dmitrikudrenko.mdrxl.sample.model.UpdateModel;
import io.github.dmitrikudrenko.mdrxl.sample.model.events.GeraltEvents;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomenRepository;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.remote.GeraltWomenRemoteRepository;
import io.github.dmitrikudrenko.mdrxl.sample.utils.EventBus;
import rx.Completable;

import javax.inject.Inject;

public final class GeraltWomenStorageCommand implements Command<GeraltWomenStorageCommandRequest> {
    private final GeraltWomenRemoteRepository geraltWomenRemoteRepository;
    private final GeraltWomenRepository geraltWomenRepository;
    private final EventBus eventBus;

    @Inject
    GeraltWomenStorageCommand(final GeraltWomenRemoteRepository geraltWomenRemoteRepository,
                              final GeraltWomenRepository geraltWomenRepository,
                              final EventBus eventBus) {
        this.geraltWomenRemoteRepository = geraltWomenRemoteRepository;
        this.geraltWomenRepository = geraltWomenRepository;
        this.eventBus = eventBus;
    }

    @Override
    public Completable execute(final GeraltWomenStorageCommandRequest request) {
        final UpdateModel model = request.getUpdateModel();
        return geraltWomenRemoteRepository.save(model)
                .doOnSuccess(success -> eventBus.post(new GeraltEvents.WomanUpdatedSuccess()))
                .doOnError(error -> eventBus.post(new GeraltEvents.WomanUpdatedFail(error)))
                .flatMapCompletable(success -> {
                    if (success) {
                        return geraltWomenRepository.save(model);
                    }
                    return Completable.complete();
                });
    }
}
