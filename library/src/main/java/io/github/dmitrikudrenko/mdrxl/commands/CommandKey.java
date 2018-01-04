package io.github.dmitrikudrenko.mdrxl.commands;

import dagger.MapKey;

@MapKey
public @interface CommandKey {
    Class<? extends CommandRequest> value();
}
