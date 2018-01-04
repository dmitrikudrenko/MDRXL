package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.commands;

import io.github.dmitrikudrenko.mdrxl.commands.CommandRequest;
import io.github.dmitrikudrenko.mdrxl.sample.model.UpdateModel;

public class GeraltWomenStorageCommandRequest extends CommandRequest {
    private final UpdateModel updateModel;

    public GeraltWomenStorageCommandRequest(final UpdateModel updateModel) {
        this.updateModel = updateModel;
    }

    UpdateModel getUpdateModel() {
        return updateModel;
    }
}
