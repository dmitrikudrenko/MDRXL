package io.github.dmitrikudrenko.core.remote;

public final class TimeoutException extends Exception {
    public TimeoutException() {
        super("Timeout exception");
    }
}
