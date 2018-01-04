package io.github.dmitrikudrenko.mdrxl.commands;

import rx.Completable;

public interface Command<R extends CommandRequest> {
    Completable execute(R request);
}
