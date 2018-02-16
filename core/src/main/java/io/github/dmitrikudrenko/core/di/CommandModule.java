package io.github.dmitrikudrenko.core.di;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import io.github.dmitrikudrenko.core.commands.GeraltVideosUpdateCommand;
import io.github.dmitrikudrenko.core.commands.GeraltVideosUpdateCommandRequest;
import io.github.dmitrikudrenko.core.commands.GeraltWomanPhotosUpdateCommand;
import io.github.dmitrikudrenko.core.commands.GeraltWomanPhotosUpdateCommandRequest;
import io.github.dmitrikudrenko.core.commands.GeraltWomanUpdateCommand;
import io.github.dmitrikudrenko.core.commands.GeraltWomanUpdateCommandRequest;
import io.github.dmitrikudrenko.core.commands.GeraltWomenStorageCommand;
import io.github.dmitrikudrenko.core.commands.GeraltWomenStorageCommandRequest;
import io.github.dmitrikudrenko.core.commands.GeraltWomenUpdateCommand;
import io.github.dmitrikudrenko.core.commands.GeraltWomenUpdateCommandRequest;
import io.github.dmitrikudrenko.mdrxl.commands.CommandKey;
import io.github.dmitrikudrenko.mdrxl.commands.CommandWithScheduler;
import rx.schedulers.Schedulers;

import javax.inject.Provider;

@Module
public final class CommandModule {
    @Provides
    @IntoMap
    @CommandKey(GeraltWomanPhotosUpdateCommandRequest.class)
    static CommandWithScheduler bindGeraltWomanPhotosUpdateCommand(final Provider<GeraltWomanPhotosUpdateCommand> provider) {
        return new CommandWithScheduler<>(provider, Schedulers.io());
    }

    @Provides
    @IntoMap
    @CommandKey(GeraltWomenStorageCommandRequest.class)
    static CommandWithScheduler bindGeraltWomenStorageCommand(final Provider<GeraltWomenStorageCommand> provider) {
        return new CommandWithScheduler<>(provider, Schedulers.io());
    }

    @Provides
    @IntoMap
    @CommandKey(GeraltWomenUpdateCommandRequest.class)
    static CommandWithScheduler bindGeraltWomenUpdateCommand(final Provider<GeraltWomenUpdateCommand> provider) {
        return new CommandWithScheduler<>(provider, Schedulers.io());
    }

    @Provides
    @IntoMap
    @CommandKey(GeraltWomanUpdateCommandRequest.class)
    static CommandWithScheduler bindGeraltWomanUpdateCommand(final Provider<GeraltWomanUpdateCommand> provider) {
        return new CommandWithScheduler<>(provider, Schedulers.io());
    }

    @Provides
    @IntoMap
    @CommandKey(GeraltVideosUpdateCommandRequest.class)
    static CommandWithScheduler bindGeraltVideosUpdateCommand(final Provider<GeraltVideosUpdateCommand> provider) {
        return new CommandWithScheduler<>(provider, Schedulers.io());
    }
}
