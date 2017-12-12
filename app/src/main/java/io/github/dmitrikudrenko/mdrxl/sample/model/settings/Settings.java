package io.github.dmitrikudrenko.mdrxl.sample.model.settings;

public final class Settings {
    private final boolean success;
    private final boolean timeout;
    private final boolean error;

    Settings(final boolean success, final boolean timeout, final boolean error) {
        this.success = success;
        this.timeout = timeout;
        this.error = error;
    }

    public static Settings success() {
        return new Settings(true, false, false);
    }

    public static Settings timeout() {
        return new Settings(false, true, false);
    }

    public static Settings error() {
        return new Settings(false, false, true);
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isTimeout() {
        return timeout;
    }

    public boolean isError() {
        return error;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "success=" + success +
                ", timeout=" + timeout +
                ", error=" + error +
                '}';
    }
}
