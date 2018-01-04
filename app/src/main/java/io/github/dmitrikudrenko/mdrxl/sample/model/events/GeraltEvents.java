package io.github.dmitrikudrenko.mdrxl.sample.model.events;

public class GeraltEvents {
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
