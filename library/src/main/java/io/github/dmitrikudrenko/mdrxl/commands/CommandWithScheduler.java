package io.github.dmitrikudrenko.mdrxl.commands;

import rx.Scheduler;

import javax.inject.Provider;

public class CommandWithScheduler<C extends Command> {
    private final Provider<C> commandProvider;
    private final Scheduler scheduler;

    public CommandWithScheduler(final Provider<C> commandProvider, final Scheduler scheduler) {
        this.commandProvider = commandProvider;
        this.scheduler = scheduler;
    }

    Command getCommand() {
        return commandProvider.get();
    }

    Scheduler getScheduler() {
        return scheduler;
    }
}
