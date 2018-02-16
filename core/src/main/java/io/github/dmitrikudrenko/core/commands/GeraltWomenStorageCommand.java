package io.github.dmitrikudrenko.core.commands;

import io.github.dmitrikudrenko.core.events.EventSender;
import io.github.dmitrikudrenko.core.events.GeraltEvents;
import io.github.dmitrikudrenko.core.local.repository.GeraltWomenRepository;
import io.github.dmitrikudrenko.core.remote.UpdateModel;
import io.github.dmitrikudrenko.core.remote.WitcherRemoteRepository;
import io.github.dmitrikudrenko.mdrxl.commands.Command;
import rx.Completable;

import javax.inject.Inject;

public class GeraltWomenStorageCommand implements Command<GeraltWomenStorageCommandRequest> {
    private final WitcherRemoteRepository witcherRemoteRepository;
    private final GeraltWomenRepository geraltWomenRepository;
    private final EventSender eventSender;

    @Inject
    GeraltWomenStorageCommand(final WitcherRemoteRepository witcherRemoteRepository,
                              final GeraltWomenRepository geraltWomenRepository,
                              final EventSender eventSender) {
        this.witcherRemoteRepository = witcherRemoteRepository;
        this.geraltWomenRepository = geraltWomenRepository;
        this.eventSender = eventSender;
    }

    @Override
    public Completable execute(final GeraltWomenStorageCommandRequest request) {
        final UpdateModel model = request.getUpdateModel();
        return witcherRemoteRepository.save(model)
                .doOnSuccess(success -> eventSender.post(new GeraltEvents.WomanUpdatedSuccess()))
                .doOnError(error -> eventSender.post(new GeraltEvents.WomanUpdatedFail(error)))
                .flatMapCompletable(success -> {
                    if (success) {
                        return geraltWomenRepository.save(model);
                    }
                    return Completable.complete();
                });
    }
}
