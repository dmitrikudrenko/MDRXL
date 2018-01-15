package io.github.dmitrikudrenko.core.events;

public final class GeraltEvents {
    public static class WomanUpdatedSuccess {
    }

    public static class WomanUpdatedFail {
        private final Throwable error;

        public WomanUpdatedFail(final Throwable error) {
            this.error = error;
        }

        public Throwable getError() {
            return error;
        }
    }
}
