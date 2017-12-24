package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.remote;

public final class TimeoutException extends Exception {
    public TimeoutException() {
        super("Timeout exception");
    }
}
