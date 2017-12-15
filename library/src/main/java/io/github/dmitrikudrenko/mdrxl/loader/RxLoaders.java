package io.github.dmitrikudrenko.mdrxl.loader;

public final class RxLoaders {
    private static int CURRENT_LOADER_ID = 0;

    private RxLoaders() {
    }

    public static int generateId() {
        CURRENT_LOADER_ID++;
        return CURRENT_LOADER_ID;
    }
}
