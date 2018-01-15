package io.github.dmitrikudrenko.core.commands;

import io.github.dmitrikudrenko.core.remote.UpdateModel;
import io.github.dmitrikudrenko.mdrxl.commands.CommandRequest;

public class GeraltWomenStorageCommandRequest extends CommandRequest {
    private final UpdateModel updateModel;

    public GeraltWomenStorageCommandRequest(final UpdateModel updateModel) {
        this.updateModel = updateModel;
    }

    UpdateModel getUpdateModel() {
        return updateModel;
    }
}
