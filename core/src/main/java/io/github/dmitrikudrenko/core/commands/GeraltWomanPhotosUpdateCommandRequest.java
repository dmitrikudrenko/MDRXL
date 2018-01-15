package io.github.dmitrikudrenko.core.commands;

import io.github.dmitrikudrenko.mdrxl.commands.CommandRequest;

public class GeraltWomanPhotosUpdateCommandRequest extends CommandRequest {
    private final long womanId;

    public GeraltWomanPhotosUpdateCommandRequest(final long womanId) {
        this.womanId = womanId;
    }

    long getWomanId() {
        return womanId;
    }
}
