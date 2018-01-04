package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.commands;

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
