package io.github.dmitrikudrenko.core.commands;

import io.github.dmitrikudrenko.mdrxl.commands.CommandRequest;

public class GeraltWomanUpdateCommandRequest extends CommandRequest {
    private final long id;

    public GeraltWomanUpdateCommandRequest(final long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
