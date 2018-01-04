package io.github.dmitrikudrenko.mdrxl.sample.di;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import io.github.dmitrikudrenko.mdrxl.commands.CommandKey;
import io.github.dmitrikudrenko.mdrxl.commands.CommandWithScheduler;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.commands.GeraltWomanPhotosUpdateCommand;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.commands.GeraltWomanPhotosUpdateCommandRequest;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.commands.GeraltWomenStorageCommand;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.commands.GeraltWomenStorageCommandRequest;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.commands.GeraltWomenUpdateCommand;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.commands.GeraltWomenUpdateCommandRequest;
import rx.schedulers.Schedulers;

import javax.inject.Provider;

@Module
class CommandModule {
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
}
