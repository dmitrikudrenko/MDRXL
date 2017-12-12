package io.github.dmitrikudrenko.mdrxl.sample.model.data.remote;

public class TimeoutException extends Exception {
    public TimeoutException() {
        super("Timeout exception");
    }
}
