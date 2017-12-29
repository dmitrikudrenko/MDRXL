package io.github.dmitrikudrenko.mdrxl.sample.utils.ui;

import android.content.res.Resources;
import android.support.annotation.StringRes;

import javax.inject.Inject;

public class ResourcesManager {
    private final Resources resources;

    @Inject
    public ResourcesManager(final Resources resources) {
        this.resources = resources;
    }

    public String getString(@StringRes final int resource, final Object... args) {
        return resources.getString(resource, args);
    }
}
